package com.api.senati.Service;

import com.api.senati.Entity.DataDocuments;
import com.aspose.words.Document;
import com.aspose.words.License;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Service
public class DocumentServiceImpl implements DocumentService {
    ObjectStorageService objectStorageService = new ObjectStorageService();
    final String ruta = "/home/hector/Documents/Senati/";
    Map<Integer, String> hashMap = new HashMap<Integer, String>() {{
        put(1, "CERT-100002");
        put( 2, "CERT-100004");
        put(3, "CERT-100012");
        put(4, "CERT-TECH01");
        put(5, "CERT-TECH02");
        put(6, "CONS-100001");
        put(7, "CONS-100002");
        put(8, "CONS-TECH01");
        put(9, "DIPL-TECH01");
    }};
    @Override
    public String crearDocumento(DataDocuments dataDocuments, Integer tipo) throws Exception {
        License license= new License();
        //ELEGIR LA PLANTILLA
        license.setLicense(ruta+"SSignerProd.lic");
        Document document= new Document(ruta+hashMap.get(tipo)+".docx");
        Class<?> dataClass = dataDocuments.getClass();
        document.getMailMerge().setUseNonMergeFields(true);
        Field[] fields= dataClass.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                System.out.println(field.getName() + ": " + field.get(dataDocuments));
                document.getMailMerge().execute(new String[]{field.getName()}, new Object[]{field.get(dataDocuments)});
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return "null";
            }
        }
        document.save("/home/hector/Documents/Senati/"+hashMap.get(tipo)+"_"+dataDocuments.getDni()+".pdf");

        return objectStorageService.saveFile("","","/home/hector/Documents/Senati/"+hashMap.get(tipo)+"_"+dataDocuments.getDni()+".pdf@"+hashMap.get(tipo)+"_"+dataDocuments.getDni());
    }
}
