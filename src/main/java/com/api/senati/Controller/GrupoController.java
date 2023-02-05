package com.api.senati.Controller;

import com.api.senati.DTO.FormatoDTO;
import com.api.senati.DTO.GrupoDTO;
import com.api.senati.Entity.Formato;
import com.api.senati.Entity.Grupo;
import com.api.senati.Security.TokenUtils;
import com.api.senati.Service.GrupoService;
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
@RequestMapping("/grupos")
public class GrupoController {
    @Autowired
    GrupoService grupoService;

    @ResponseBody
    @GetMapping
    public ResponseEntity<HashMap<String, Object>> listarGrupos(@RequestHeader("Authorization") String token) {
        HashMap<String, Object> responseMap = new HashMap<>();
        try {
            List<Grupo> grupos = grupoService.obtenerListaGrupos();
            responseMap.put("codigo", 1);
            responseMap.put("msg", "Solicitud exitosa.");
            responseMap.put("grupos", grupos);
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        } catch (Exception e) {
            responseMap.put("codigo", -1);
            responseMap.put("msg", "Solicitud fallida.");
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @GetMapping("{idGrupo}")
    public ResponseEntity<HashMap<String, Object>> obtenerGrupo(@RequestHeader("Authorization") String token, @PathVariable("idGrupo") String idGrupo) {
        HashMap<String, Object> responseMap = new HashMap<>();
        int idF;
        try {
            idF = Integer.parseInt(idGrupo);
            Grupo grupo = grupoService.obtenerGrupo(idF);
            if (grupo == null) {
                responseMap.put("codigo", 0);
                responseMap.put("msg", "Solicitud exitosa.");
                responseMap.put("grupo", "No existe el grupo con el id " + idGrupo);
                return new ResponseEntity<>(responseMap, HttpStatus.NO_CONTENT);
            }
            responseMap.put("codigo", 1);
            responseMap.put("msg", "Solicitud exitosa.");
            responseMap.put("grupo", grupo);
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        } catch (Exception e) {
            responseMap.put("codigo", -1);
            responseMap.put("msg", "Solicitud fallida.");
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @PostMapping
    public ResponseEntity<HashMap<String, Object>> crearGrupo(@RequestHeader("Authorization") String token, @RequestBody Grupo grupo) {
        HashMap<String, Object> responseMap = new HashMap<>();
        try {
            grupo.setModpor(obtenerIdUsuarioLogeado(token));
            grupo.setRegpor(obtenerIdUsuarioLogeado(token));
            Grupo grupo1 = grupoService.crearGrupo(grupo);
            responseMap.put("codigo", 1);
            responseMap.put("msg", "Solicitud exitosa.");
            responseMap.put("grupo", grupo1);
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
    @PutMapping("{idGrupo}")
    public ResponseEntity<HashMap<String, Object>> editarGrupo(@RequestHeader("Authorization") String token, @PathVariable("idGrupo") String idGrupo, @RequestBody GrupoDTO grupoDTO) {
        HashMap<String, Object> responseMap = new HashMap<>();
        int id;
        try {
            id = Integer.parseInt(idGrupo);

            Grupo grupo = grupoService.editarGrupo(id, grupoDTO, obtenerIdUsuarioLogeado(token));
            responseMap.put("codigo", 1);
            responseMap.put("msg", "Solicitud exitosa.");
            responseMap.put("grupo", grupo);
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
    @DeleteMapping("{idGrupo}")
    public ResponseEntity<HashMap<String, Object>> eliminarGrupo(@RequestHeader("Authorization") String token, @PathVariable("idGrupo") String idGrupo) {
        HashMap<String, Object> responseMap = new HashMap<>();
        int id;
        try {
            id = Integer.parseInt(idGrupo);
            if (grupoService.eliminarGrupo(id, obtenerIdUsuarioLogeado(token))) {
                responseMap.put("codigo", 1);
                responseMap.put("msg", "Solicitud exitosa.");
                responseMap.put("grupo", "Eliminado");
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
