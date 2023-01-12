package org.fdcaballero.msvccursos.controller;

import org.fdcaballero.msvccursos.models.Usuario;
import org.fdcaballero.msvccursos.models.entity.Curso;
import org.fdcaballero.msvccursos.services.CursoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@RestController
@RequestMapping("api/curso")
public class CursoController {


    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public List<Curso> getAll() {
        return this.cursoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> getById(@PathVariable Long id) {
        Optional<Curso> curso = this.cursoService.getUsersByCurso(id);
        return curso.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Curso curso, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return ResponseEntity.badRequest().body(this.fieldHasError.apply(bindingResult));
        return ResponseEntity.ok(this.cursoService.save(curso));

    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Curso curso, BindingResult bindingResult, @PathVariable Long id) {
        if (bindingResult.hasErrors()) return ResponseEntity.badRequest().body(this.fieldHasError.apply(bindingResult));
        Curso cursoDB = this.cursoService.update(curso, id);
        return (cursoDB == null) ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(cursoDB);
    }


    @PutMapping("/asignar-usuario/{cursoId}")
    public ResponseEntity<?> asignarCurso(@RequestBody Usuario user, @PathVariable Long cursoId) {
        Optional<Usuario> usuario = this.cursoService.asignarUsuario(user, cursoId);
        return (usuario.isPresent()) ?
                ResponseEntity.status(HttpStatus.CREATED).body(usuario.get())
                : ResponseEntity.badRequest().build();

    }

    @PostMapping("/crear-usuario/{cursoId}")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario user, @PathVariable Long cursoId) {
        Optional<Usuario> usuario = this.cursoService.crearUsuario(user, cursoId);
        return (usuario.isPresent()) ?
                ResponseEntity.status(HttpStatus.CREATED).body(usuario.get())
                : ResponseEntity.badRequest().build();

    }

    @DeleteMapping("/eliminar-usuario/{cursoId}")
    public ResponseEntity<?> deleteUser(@RequestBody Usuario user, @PathVariable Long cursoId) {
        Optional<Usuario> response = this.cursoService.eliminarUsuario(user, cursoId);
        if (response.isPresent()) return ResponseEntity.status(HttpStatus.OK).body(response.get());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        return (this.cursoService.delete(id)) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
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

    @DeleteMapping("/eliminar-curso-usuario/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        this.cursoService.deleteCursoUsuarioById(id);
        return ResponseEntity.noContent().build();
    }
}
