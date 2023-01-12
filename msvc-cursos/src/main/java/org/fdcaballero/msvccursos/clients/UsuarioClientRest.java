package org.fdcaballero.msvccursos.clients;

import org.fdcaballero.msvccursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-usuarios", url = " :8081")
public interface UsuarioClientRest {


    @GetMapping("api/usuarios/{id}")
    Usuario detalle(@PathVariable Long id);

    @GetMapping("api/usuarios/user")
    List<Usuario> getAllUsersById(@RequestParam List<Long> paramId);


    @PostMapping("api/usuarios")
    Usuario create(@RequestBody Usuario user);



}
