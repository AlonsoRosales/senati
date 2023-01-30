package com.api.senati.Controller;

import com.api.senati.Entity.Firmante;
import com.api.senati.Service.FirmanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@Controller
@CrossOrigin("*")
public class FirmanteController {
    @Autowired
    FirmanteService firmanteService;

    //Lista de firmantes por id dependencia
    @ResponseBody
    @GetMapping("/firmantes/")
    public ResponseEntity<HashMap<String, Object>> listarFirmantes(@RequestParam(value = "dependencia") String idDependencia) {
        HashMap<String, Object> responseMap = new HashMap<>();
        int id;
        try {
            id = Integer.parseInt(idDependencia);
            List<Firmante> firmantes = firmanteService.findByIdDependencia(id);
            responseMap.put("codigo", 1);
            responseMap.put("msg", "Solicitud exitosa.");
            responseMap.put("firmantes", firmantes);
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        } catch (Exception e) {
            responseMap.put("codigo", -1);
            responseMap.put("msg", "Solicitud fallida.");
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }

    }

    @ResponseBody
    @GetMapping("/firmantes/{idFirmante}")
    public ResponseEntity<HashMap<String, Object>> obtenerFirmante(@PathVariable("idFirmante") String idFirmante, @RequestParam(value = "dependencia") String idDependencia) {
        HashMap<String, Object> responseMap = new HashMap<>();
        int idF, idD;
        try {
            idF = Integer.parseInt(idFirmante);
            idD = Integer.parseInt(idDependencia);
            Firmante firmante = firmanteService.findByIdDependenciaAndIdFirmante(idD,idF);
            responseMap.put("codigo", 1);
            responseMap.put("msg", "Solicitud exitosa.");
            responseMap.put("firmante", firmante);
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        } catch (Exception e) {
            responseMap.put("codigo", -1);
            responseMap.put("msg", "Solicitud fallida.");
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }
    }
    @ResponseBody
    @PostMapping("/firmantes")
    public ResponseEntity<HashMap<String, Object>> crearFirmante(@RequestBody Firmante firmante){
        HashMap<String, Object> responseMap = new HashMap<>();
        try {
            firmanteService.createFirmante(firmante);
            responseMap.put("codigo", 1);
            responseMap.put("msg", "Solicitud exitosa.");
            responseMap.put("firmante", firmante);
            return new ResponseEntity<>(responseMap, HttpStatus.CREATED);
        }catch (ConstraintViolationException e){
            e.getConstraintViolations().forEach(violation -> {
                responseMap.put(violation.getPropertyPath().toString(), violation.getMessage());
            });
            responseMap.put("codigo", -1);
            responseMap.put("msg", "Solicitud fallida.");
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }
    }

}
