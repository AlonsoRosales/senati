package com.api.senati.Service;

import com.obs.services.ObsClient;
import com.obs.services.model.ObjectMetadata;
import com.obs.services.model.ObsObject;
import com.obs.services.model.PutObjectResult;
import org.apache.tika.Tika;
import org.apache.tika.mime.MediaType;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;

public class ObjectStorageService {
    final String accessKey = "LUNFUZJVTPLD0AFE0VHM";
    final String secretKey = "wUpfjLupFnPyKXKkDdrLpkMh8ct8hcCuOcw9YrJc";
    final String endPoint = "obs.la-south-2.myhuaweicloud.com";
    final String bucketName = "senati-storag";

    public String saveFile(String base64, String docide, String document) {
        try {
            // Configure OBS client with access details
            ObsClient obsClient = new ObsClient(accessKey, secretKey, endPoint);

            // Convert base64 string to input stream
            InputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(base64));
            byte[] data = Base64.getDecoder().decode(base64.getBytes());

            Tika tika = new Tika();
            String mimeType = tika.detect(data);
            MediaType mediaType = MediaType.parse(mimeType);
            String extension = mediaType.getSubtype();
            String fileName = docide.equals("") ? document : docide;
            // Save image to OBS server
            PutObjectResult object = obsClient.putObject(bucketName, fileName + "." + extension, stream, new ObjectMetadata());

            return object.getObjectUrl();
        } catch (Exception e) {
            return "-1";
        }
    }

    public String getFile(String ruta) {
        return null;
    }
}
