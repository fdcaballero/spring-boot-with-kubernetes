package org.fdcaballero.msusuarios.controller;

import lombok.extern.log4j.Log4j2;
import org.fdcaballero.msusuarios.models.entity.Usuario;
import org.fdcaballero.msusuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.Valid;
import java.util.*;
import java.util.function.Function;

@Log4j2
@RestController
@RequestMapping("api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/listar")
    public List<Usuario> getAll() {
        return this.usuarioService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> details(@PathVariable Long id) {
        Optional<Usuario> usuario = this.usuarioService.findById(id);
        return (usuario.isPresent()) ? ResponseEntity.ok(usuario.get())
                : ResponseEntity.badRequest().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@Valid @RequestBody Usuario usuario, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(fieldHasError.apply(bindingResult));
        }
        if (this.usuarioService.existUserByEmail(usuario.getEmail())) return ResponseEntity
                .badRequest().body(Collections.singletonMap("mensaje", "Ya existe un usuario con ese correo"));
        return ResponseEntity.ok(this.usuarioService.save(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Usuario usuario, BindingResult bindingResult, @PathVariable Long id) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(fieldHasError.apply(bindingResult));
        }
        if (this.usuarioService.existUserByEmail(usuario.getEmail())) return ResponseEntity
                .badRequest().body(Collections.singletonMap("mensaje", "Ya existe un usuario con ese correo"));
        Usuario userBd = this.usuarioService.update(usuario, id);
        return (userBd == null) ? ResponseEntity.notFound().build() :
                ResponseEntity.status(HttpStatus.CREATED).body(userBd);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return (this.usuarioService.detele(id)) ? ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }

    Function<BindingResult, Map<String, String>> fieldHasError = bindingResult -> {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(fieldError -> {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            });
            return errors;
        }
        return null;
    };

    @GetMapping("/user")
    public ResponseEntity<List<Usuario>> getAllUserByIds(@RequestParam List<Long> paramId) {
        List<Usuario> userAllById = this.usuarioService.findAllById(paramId);
        return !userAllById.isEmpty() ? ResponseEntity.ok(userAllById) : ResponseEntity.notFound().build();

    }
}
