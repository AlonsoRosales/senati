package com.api.senati.Controller;

import com.api.senati.DTO.FormatoDTO;
import com.api.senati.Entity.Formato;
import com.api.senati.Security.TokenUtils;
import com.api.senati.Service.FormatoService;
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
@RequestMapping("/formatos")
public class FormatoController {
    @Autowired
    FormatoService formatoService;

    @ResponseBody
    @GetMapping
    public ResponseEntity<HashMap<String, Object>> listarFormatos(@RequestHeader("Authorization") String token) {
        HashMap<String, Object> responseMap = new HashMap<>();
        try {
            List<Formato> formatos = formatoService.obtenerListaDeFormatos();
            responseMap.put("codigo", 1);
            responseMap.put("msg", "Solicitud exitosa.");
            responseMap.put("formatos", formatos);
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        } catch (Exception e) {
            responseMap.put("codigo", -1);
            responseMap.put("msg", "Solicitud fallida.");
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }

    }

    @ResponseBody
    @GetMapping("{idFormato}")
    public ResponseEntity<HashMap<String, Object>> obtenerFormato(@RequestHeader("Authorization") String token, @PathVariable("idFormato") String idFormato) {
        HashMap<String, Object> responseMap = new HashMap<>();
        int idF;
        try {
            idF = Integer.parseInt(idFormato);
            Formato formato = formatoService.obtenerFormato(idF);
            if (formato == null) {
                responseMap.put("codigo", 0);
                responseMap.put("msg", "Solicitud exitosa.");
                responseMap.put("formato", "No existe el formato con el id " + idFormato);
                return new ResponseEntity<>(responseMap, HttpStatus.NO_CONTENT);
            }
            responseMap.put("codigo", 1);
            responseMap.put("msg", "Solicitud exitosa.");
            responseMap.put("formato", formato);
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        } catch (Exception e) {
            responseMap.put("codigo", -1);
            responseMap.put("msg", "Solicitud fallida.");
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @PostMapping
    public ResponseEntity<HashMap<String, Object>> crearFormato(@RequestHeader("Authorization") String token, @RequestBody Formato formato) {
        HashMap<String, Object> responseMap = new HashMap<>();
        try {
            formato.setModpor(obtenerIdUsuarioLogeado(token));
            formato.setRegpor(obtenerIdUsuarioLogeado(token));
            Formato formato1 = formatoService.crearFormato(formato);
            responseMap.put("codigo", 1);
            responseMap.put("msg", "Solicitud exitosa.");
            responseMap.put("formato", formato);
            return new ResponseEntity<>(responseMap, HttpStatus.CREATED);
        } catch (ConstraintViolationException e) {
            e.getConstraintViolations().forEach(violation -> {
                responseMap.put(violation.getPropertyPath().toString(), violation.getMessage());
            });
            responseMap.put("codigo", -1);
            responseMap.put("msg", "Solicitud fallida.");
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @PutMapping("{idFormato}")
    public ResponseEntity<HashMap<String, Object>> editarFormato(@RequestHeader("Authorization") String token, @PathVariable("idFormato") String idFormato, @RequestBody FormatoDTO formatoDTO) {
        HashMap<String, Object> responseMap = new HashMap<>();
        int id;
        try {
            id = Integer.parseInt(idFormato);

            Formato formato = formatoService.editarFormato(id, formatoDTO, obtenerIdUsuarioLogeado(token));
            responseMap.put("codigo", 1);
            responseMap.put("msg", "Solicitud exitosa.");
            responseMap.put("formato", formato);
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
    @DeleteMapping("{idFormato}")
    public ResponseEntity<HashMap<String, Object>> eliminarFormato(@RequestHeader("Authorization") String token, @PathVariable("idFormato") String idFormato) {
        HashMap<String, Object> responseMap = new HashMap<>();
        int id;
        try {
            id = Integer.parseInt(idFormato);
            if (formatoService.eliminarFormato(id, obtenerIdUsuarioLogeado(token))) {
                responseMap.put("codigo", 1);
                responseMap.put("msg", "Solicitud exitosa.");
                responseMap.put("formato", "Eliminado");
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
