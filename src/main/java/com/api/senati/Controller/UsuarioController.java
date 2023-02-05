package com.api.senati.Controller;

import com.api.senati.DTO.FirmanteDTO;
import com.api.senati.DTO.UsuarioDTO;
import com.api.senati.Entity.Firmante;
import com.api.senati.Entity.Usuario;
import com.api.senati.Security.TokenUtils;
import com.api.senati.Service.UsuarioService;
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
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    @ResponseBody
    @GetMapping
    public ResponseEntity<HashMap<String, Object>> listarUsuarios(@RequestHeader("Authorization") String token) {
        HashMap<String, Object> responseMap = new HashMap<>();
        int id = obtenerIdUsuarioLogeado(token);
        try {
            List<Usuario> usuarios = usuarioService.obtenerUsuarios();
            responseMap.put("codigo", 1);
            responseMap.put("msg", "Solicitud exitosa.");
            responseMap.put("usuarios", usuarios);
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("codigo", -1);
            responseMap.put("msg", "Solicitud fallida.");
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }

    }

    @ResponseBody
    @GetMapping("{idUsuario}")
    public ResponseEntity<HashMap<String, Object>> obetnerUsuario(@RequestHeader("Authorization") String token, @PathVariable("idUsuario") String idUsuario) {
        HashMap<String, Object> responseMap = new HashMap<>();
        int idUsuarioLog = obtenerIdUsuarioLogeado(token);
        int idU;
        try {
            idU = Integer.parseInt(idUsuario);
            Usuario usuario = usuarioService.obetenerUsuario(idU);
            responseMap.put("codigo", 1);
            responseMap.put("msg", "Solicitud exitosa.");
            responseMap.put("usuario", usuario);
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("codigo", -1);
            responseMap.put("msg", "Solicitud fallida.");
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @PostMapping
    public ResponseEntity<HashMap<String, Object>> crearUsuario(@RequestHeader("Authorization") String token,@RequestBody Usuario usuario) {
        HashMap<String, Object> responseMap = new HashMap<>();
        try {
            Usuario usuario1 = usuarioService.crearUsuario(usuario);
            responseMap.put("codigo", 1);
            responseMap.put("msg", "Solicitud exitosa.");
            responseMap.put("usuario", usuario1);
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
    @PutMapping("{idUsuario}")
    public ResponseEntity<HashMap<String, Object>> editarFirmante(@RequestHeader("Authorization") String token, @PathVariable("idUsuario") String idUsuario, @RequestBody UsuarioDTO usuario) {
        HashMap<String, Object> responseMap = new HashMap<>();
        int idUserLog = obtenerIdUsuarioLogeado(token);
        int id;
        try {
            id = Integer.parseInt(idUsuario);
            Usuario usuario1 = usuarioService.editarUsuario(id,usuario,idUserLog);
            responseMap.put("codigo", 1);
            responseMap.put("msg", "Solicitud exitosa.");
            responseMap.put("usuario", usuario1);
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
    @DeleteMapping("{idUsuario}")
    public ResponseEntity<HashMap<String, Object>> eliminarUsuario(@RequestHeader("Authorization") String token, @PathVariable("idUsuario") String idUsuario) {
        HashMap<String, Object> responseMap = new HashMap<>();
        int id;
        try {
            id = Integer.parseInt(idUsuario);
            if (usuarioService.eliminarUsuario(id,obtenerIdUsuarioLogeado(token))) {
                responseMap.put("codigo", 1);
                responseMap.put("msg", "Solicitud exitosa.");
                responseMap.put("usuario", "Eliminado");
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
