package org.fdcaballero.msvccursos.services;

import org.fdcaballero.msvccursos.models.Usuario;
import org.fdcaballero.msvccursos.models.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {

    List<Curso> findAll();

    Optional<Curso> findById(Long id);

    Curso save(Curso curso);

    boolean delete(Long id);

    Curso update(Curso curso, Long id);

    Optional<Usuario> asignarUsuario(Usuario user, Long id);

    Optional<Usuario> crearUsuario(Usuario user, Long id);

    Optional<Usuario> eliminarUsuario(Usuario user, Long id);

    Optional<Curso> getUsersByCurso(Long cursoId);

    void deleteCursoUsuarioById(Long id);
}
