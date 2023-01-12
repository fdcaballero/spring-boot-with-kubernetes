package org.fdcaballero.msusuarios.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-cursos", url = "host.docker.internal:8082")
public interface CursoClientRest {

    @DeleteMapping("api/curso/eliminar-curso-usuario/{id}")
    void deleteUserById(@PathVariable Long id);
}
