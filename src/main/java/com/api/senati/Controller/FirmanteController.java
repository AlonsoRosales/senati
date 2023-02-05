package com.api.senati.Controller;

import com.api.senati.Config.CustomException;
import com.api.senati.DTO.FirmanteDTO;
import com.api.senati.Entity.Firmante;
import com.api.senati.Security.TokenUtils;
import com.api.senati.Service.FirmanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;

@RestController
@Controller
@CrossOrigin("*")
@RequestMapping("/firmantes")
public class FirmanteController {
    @Autowired
    FirmanteService firmanteService;

    //Lista de firmantes por id dependencia
    @ResponseBody
    @GetMapping
    public ResponseEntity<HashMap<String, Object>> listarFirmantes(@RequestHeader("Authorization") String token, @RequestParam(value = "dependencia") String idDependencia) {
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
    @GetMapping("{idFirmante}")
    public ResponseEntity<HashMap<String, Object>> obtenerFirmante(@RequestHeader("Authorization") String token, @PathVariable("idFirmante") String idFirmante, @RequestParam(value = "dependencia") String idDependencia) {
        HashMap<String, Object> responseMap = new HashMap<>();
        int idF, idD;
        try {
            idF = Integer.parseInt(idFirmante);
            idD = Integer.parseInt(idDependencia);
            Firmante firmante = firmanteService.findByIdDependenciaAndIdFirmante(idD, idF);
            if (firmante == null) {
                responseMap.put("codigo", 0);
                responseMap.put("msg", "Solicitud exitosa.");
                responseMap.put("firmante", "No existe el firmante con el id " + idFirmante);
                return new ResponseEntity<>(responseMap, HttpStatus.NO_CONTENT);
            }
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
    @PostMapping
    public ResponseEntity<HashMap<String, Object>> crearFirmante(@RequestHeader("Authorization") String token, @RequestBody Firmante firmante) {
        HashMap<String, Object> responseMap = new HashMap<>();
        try {
            Firmante firmante1 = firmanteService.createFirmante(firmante);
            responseMap.put("codigo", 1);
            responseMap.put("msg", "Solicitud exitosa.");
            responseMap.put("firmante", firmante1);
            return new ResponseEntity<>(responseMap, HttpStatus.CREATED);
        } catch (ConstraintViolationException e) {
            e.getConstraintViolations().forEach(violation -> {
                responseMap.put(violation.getPropertyPath().toString(), violation.getMessage());
            });
            responseMap.put("codigo", -1);
            responseMap.put("msg", "Solicitud fallida.");
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        } catch (CustomException customException) {
            responseMap.put("codigo", 0);
            responseMap.put("msg", customException.getMessage());
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @PutMapping("{idFirmante}")
    public ResponseEntity<HashMap<String, Object>> editarFirmante(@RequestHeader("Authorization") String token, @PathVariable("idFirmante") String idFirmante, @RequestBody FirmanteDTO firmanteDTO) {
        HashMap<String, Object> responseMap = new HashMap<>();
        int id;
        try {
            id = Integer.parseInt(idFirmante);
            Firmante firmanteDTO1 = firmanteService.actualizarFirmante(id, firmanteDTO);
            responseMap.put("codigo", 1);
            responseMap.put("msg", "Solicitud exitosa.");
            responseMap.put("firmante", firmanteDTO1);
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            e.getConstraintViolations().forEach(violation -> {
                responseMap.put(violation.getPropertyPath().toString(), violation.getMessage());
            });
            responseMap.put("codigo", -1);
            responseMap.put("msg", "Solicitud fallida.");
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            responseMap.put("codigo", -2);
            responseMap.put("msg", exception.getMessage());
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @DeleteMapping("{idFirmante}")
    public ResponseEntity<HashMap<String, Object>> eliminarFirmante(@RequestHeader("Authorization") String token, @PathVariable("idFirmante") String idFirmante) {
        HashMap<String, Object> responseMap = new HashMap<>();
        int id;
        try {
            id = Integer.parseInt(idFirmante);
            if (firmanteService.eliminarFirmante(id, obtenerIdUsuarioLogeado(token))) {
                responseMap.put("codigo", 1);
                responseMap.put("msg", "Solicitud exitosa.");
                responseMap.put("firmante", "Eliminado");
                return new ResponseEntity<>(responseMap, HttpStatus.OK);
            } else {
                responseMap.put("codigo", -1);
                responseMap.put("msg", "Solicitud fallida.");
                return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception exception) {
            responseMap.put("codigo", -2);
            responseMap.put("msg", exception.getMessage());
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }
    }

    public Integer obtenerIdUsuarioLogeado(String token) {
        TokenUtils tokenUtils = new TokenUtils();
        return tokenUtils.idUsuario(token);
    }

}
