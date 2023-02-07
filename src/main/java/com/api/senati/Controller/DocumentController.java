package com.api.senati.Controller;

import com.api.senati.Entity.DataDocuments;
import com.api.senati.Entity.Formato;
import com.api.senati.Service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;

@RestController
@Controller
@CrossOrigin("*")
@RequestMapping("/documentos")
public class DocumentController {
    @Autowired
    DocumentService documentService;
    @ResponseBody
    @PostMapping
    public ResponseEntity<HashMap<String, Object>> crearDocumento(@RequestHeader("Authorization") String token, @RequestBody DataDocuments dataDocuments,@RequestParam Integer tipo) {
        HashMap<String, Object> responseMap = new HashMap<>();
        try {
            String formato1 = documentService.crearDocumento(dataDocuments,tipo);
            responseMap.put("codigo", 1);
            responseMap.put("msg", "Solicitud exitosa.");
            responseMap.put("formato", formato1);
            return new ResponseEntity<>(responseMap, HttpStatus.CREATED);
        } catch (ConstraintViolationException e) {
            e.getConstraintViolations().forEach(violation -> {
                responseMap.put(violation.getPropertyPath().toString(), violation.getMessage());
            });
            responseMap.put("codigo", -1);
            responseMap.put("msg", "Solicitud fallida.");
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("codigo", -1);
            responseMap.put("msg", "Solicitud fallida.");
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }
    }
}