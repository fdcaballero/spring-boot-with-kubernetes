package org.fdcaballero.msvccursos.services;

import feign.FeignException;
import org.fdcaballero.msvccursos.clients.UsuarioClientRest;
import org.fdcaballero.msvccursos.models.Usuario;
import org.fdcaballero.msvccursos.models.entity.Curso;
import org.fdcaballero.msvccursos.models.entity.CursoUsuario;
import org.fdcaballero.msvccursos.repository.CursoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CursoServiceImpl implements CursoService {

    private final CursoRepository repository;
    private final UsuarioClientRest clientRestUser;

    public CursoServiceImpl(CursoRepository repository, UsuarioClientRest clientRestUser) {
        this.repository = repository;
        this.clientRestUser = clientRestUser;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Curso> findAll() {
        return this.repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> findById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    @Transactional
    public Curso save(Curso curso) {
        return this.repository.save(curso);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        boolean isPresent = this.repository.existsById(id);
        if (!isPresent) return false;
        this.repository.deleteById(id);
        return true;

    }

    @Override
    @Transactional
    public Curso update(Curso curso, Long id) {
        Optional<Curso> cursoDB = this.repository.findById(id);
        if (cursoDB.isEmpty()) return null;
        cursoDB.get().setName(curso.getName());
        return cursoDB.get();
    }

    @Override
    @Transactional
    public Optional<Usuario> asignarUsuario(Usuario user, Long cursoId) throws FeignException {
        Optional<Curso> cursoBD = repository.findById(cursoId);
        try {
            if (cursoBD.isPresent()) {
                Usuario usuarioBD = this.clientRestUser.detalle(user.getId());
                if (usuarioBD != null) {
                    CursoUsuario cursoUsuario = new CursoUsuario();
                    cursoUsuario.setUsuarioId(usuarioBD.getId());
                    cursoBD.get().addCursoUsuario(cursoUsuario);
                    return Optional.of(usuarioBD);
                }
            }
            return Optional.empty();
        } catch (FeignException feignException) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Optional<Usuario> crearUsuario(Usuario user, Long cursoId) throws FeignException {
        Optional<Curso> cursoBD = this.repository.findById(cursoId);
        try {
            if (cursoBD.isPresent()) {
                Usuario usuarioBD = this.clientRestUser.create(user);
                CursoUsuario cursoUsuario = new CursoUsuario();
                cursoUsuario.setUsuarioId(usuarioBD.getId());
                cursoBD.get().addCursoUsuario(cursoUsuario);
                return Optional.of(usuarioBD);
            }
            return Optional.empty();
        } catch (FeignException feignException) {
            return Optional.empty();
        }

    }

    @Override
    @Transactional
    public Optional<Usuario> eliminarUsuario(Usuario user, Long cursoId) throws FeignException {
        Optional<Curso> cursoBD = this.repository.findById(cursoId);
        try {
            if (cursoBD.isPresent()) {
                Usuario userDB = this.clientRestUser.detalle(user.getId());
                if (userDB != null) {
                    CursoUsuario cursoUsuario = new CursoUsuario();
                    cursoUsuario.setUsuarioId(userDB.getId());
                    cursoBD.get().removeCursoUsuario(cursoUsuario);
                    this.repository.save(cursoBD.get());
                    return Optional.of(userDB);
                }
            }
            return Optional.empty();
        } catch (FeignException feignException) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> getUsersByCurso(Long cursoId) {
        Optional<Curso> cursoBD = this.repository.findById(cursoId);
        if (cursoBD.isPresent()) {
            List<Usuario> usersById = this.clientRestUser
                    .getAllUsersById(cursoBD.get()
                            .getCursosUsuarios().stream()
                            .map(CursoUsuario::getUsuarioId).toList());
            cursoBD.get().setUsuarios(usersById);
            return cursoBD;
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void deleteCursoUsuarioById(Long id) {
        this.repository.deleteCursoUsuarioById(id);
    }
}