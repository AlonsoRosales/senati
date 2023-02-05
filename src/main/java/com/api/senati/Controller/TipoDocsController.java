package com.api.senati.Controller;

import com.api.senati.DTO.TipoDocDTO;
import com.api.senati.Entity.TipoDocs;
import com.api.senati.Security.TokenUtils;
import com.api.senati.Service.TipoDocService;
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
@RequestMapping("/tipo-docs")
public class TipoDocsController {
    @Autowired
    TipoDocService tipoDocService;
    @ResponseBody
    @GetMapping
    public ResponseEntity<HashMap<String, Object>> litarTipoDocs(@RequestHeader("Authorization") String token) {
        HashMap<String, Object> responseMap = new HashMap<>();
        try {
            List<TipoDocs> tipoDocs = tipoDocService.listarTipoDocs();
            responseMap.put("codigo", 1);
            responseMap.put("msg", "Solicitud exitosa.");
            responseMap.put("tipoDocs", tipoDocs);
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        } catch (Exception e) {
            responseMap.put("codigo", -1);
            responseMap.put("msg", "Solicitud fallida.");
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }
    }
    @ResponseBody
    @GetMapping("{idTipoDoc}")
    public ResponseEntity<HashMap<String, Object>> obtenerTipoDoc(@RequestHeader("Authorization") String token, @PathVariable("idTipoDoc") String idTipoDoc) {
        HashMap<String, Object> responseMap = new HashMap<>();
        int idF;
        try {
            idF = Integer.parseInt(idTipoDoc);
            TipoDocs tipoDoc = tipoDocService.obtenerTipoDoc(idF);
            if (tipoDoc == null) {
                responseMap.put("codigo", 0);
                responseMap.put("msg", "Solicitud exitosa.");
                responseMap.put("tipoDoc", "No existe el tipo de documento con el id " + idTipoDoc);
                return new ResponseEntity<>(responseMap, HttpStatus.NO_CONTENT);
            }
            responseMap.put("codigo", 1);
            responseMap.put("msg", "Solicitud exitosa.");
            responseMap.put("tipoDoc", tipoDoc);
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        } catch (Exception e) {
            responseMap.put("codigo", -1);
            responseMap.put("msg", "Solicitud fallida.");
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @PostMapping
    public ResponseEntity<HashMap<String, Object>> crearTipoDoc(@RequestHeader("Authorization") String token, @RequestBody TipoDocs tipoDoc) {
        HashMap<String, Object> responseMap = new HashMap<>();
        try {
            tipoDoc.setModpor(obtenerIdUsuarioLogeado(token));
            tipoDoc.setRegpor(obtenerIdUsuarioLogeado(token));
            TipoDocs tipoDoc1 = tipoDocService.crearTipoDoc(tipoDoc);
            responseMap.put("codigo", 1);
            responseMap.put("msg", "Solicitud exitosa.");
            responseMap.put("tipoDoc", tipoDoc1);
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
    @PutMapping("{idTipoDoc}")
    public ResponseEntity<HashMap<String, Object>> editarGrupo(@RequestHeader("Authorization") String token, @PathVariable("idTipoDoc") String idTipoDoc, @RequestBody TipoDocDTO tipoDocDTO) {
        HashMap<String, Object> responseMap = new HashMap<>();
        int id;
        try {
            id = Integer.parseInt(idTipoDoc);

            TipoDocs tipoDoc = tipoDocService.editarTipoDoc(id, tipoDocDTO, obtenerIdUsuarioLogeado(token));
            responseMap.put("codigo", 1);
            responseMap.put("msg", "Solicitud exitosa.");
            responseMap.put("tipoDoc", tipoDoc);
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
    @DeleteMapping("{idTipoDoc}")
    public ResponseEntity<HashMap<String, Object>> eliminarGrupo(@RequestHeader("Authorization") String token, @PathVariable("idTipoDoc") String idTipoDoc) {
        HashMap<String, Object> responseMap = new HashMap<>();
        int id;
        try {
            id = Integer.parseInt(idTipoDoc);
            if (tipoDocService.eliminarTipoDoc(id, obtenerIdUsuarioLogeado(token))) {
                responseMap.put("codigo", 1);
                responseMap.put("msg", "Solicitud exitosa.");
                responseMap.put("tipoDoc", "Eliminado");
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
