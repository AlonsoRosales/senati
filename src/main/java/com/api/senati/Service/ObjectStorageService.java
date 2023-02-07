package com.api.senati.Service;

import com.obs.services.ObsClient;
import com.obs.services.model.*;
import org.apache.tika.Tika;
import org.apache.tika.mime.MediaType;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
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
            String extension, fileName = "";
            InputStream stream;
            if (!base64.isEmpty()) {
                System.out.println("ESTOY ENTRANDO");
                stream = new ByteArrayInputStream(Base64.getDecoder().decode(base64));
                byte[] data = Base64.getDecoder().decode(base64.getBytes());
                Tika tika = new Tika();
                String mimeType = tika.detect(data);
                MediaType mediaType = MediaType.parse(mimeType);
                extension = mediaType.getSubtype();
                fileName = docide;
            } else {
                fileName = document.split("@")[1];
                extension = "pdf";
                File file = new File(document.split("@")[0]);
                stream = new FileInputStream(file);
            }
            // Save image to OBS server
            System.out.println(bucketName+ fileName + "." + extension);
            PutObjectResult object = obsClient.putObject(bucketName, fileName + "." + extension, stream, new ObjectMetadata());
            stream.close();
            return object.getObjectUrl();
        } catch (Exception e) {
            e.printStackTrace();
            return "-1";
        }
    }

    public String getFile(String fileName) {
        try {
            ObsClient obsClient = new ObsClient(accessKey, secretKey, endPoint);
            // Set the expiration time of the URL to 60 minutes
            long expiration = System.currentTimeMillis() / 1000 + 60 * 60;
            //Specify the requested operation.
            TemporarySignatureRequest request = new TemporarySignatureRequest(HttpMethodEnum.PUT, expiration);
            //Specify the bucket name and object name involved in this operation.
            request.setBucketName(bucketName);
            request.setObjectKey(fileName + ".pdf");
            TemporarySignatureResponse response = obsClient.createTemporarySignature(request);
            obsClient.close();
            //If the following message is returned, the presigned URL is successfully issued, and you can print the URL information.
            return response.getSignedUrl();
        } catch (Exception e) {
            return "-1";
        }

    }
}
