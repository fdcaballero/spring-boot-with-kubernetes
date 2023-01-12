package org.fdcaballero.msusuarios.services.Implementation;

import org.fdcaballero.msusuarios.Repository.UsuarioRepository;
import org.fdcaballero.msusuarios.clients.CursoClientRest;
import org.fdcaballero.msusuarios.models.entity.Usuario;
import org.fdcaballero.msusuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;


    private final CursoClientRest clientRest;

    public UsuarioServiceImpl(UsuarioRepository repository, CursoClientRest clientRest) {
        this.repository = repository;
        this.clientRest = clientRest;

    }


    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return this.repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findById(Long id) {
        return this.repository.findById(id);

    }

    @Override
    @Transactional
    public Usuario save(Usuario usuario) {
        return this.repository.save(usuario);
    }

    @Override
    @Transactional
    public Boolean detele(Long id) {
        Optional<Usuario> usuario = this.repository.findById(id);
        if (usuario.isEmpty()) return false;
        this.repository.deleteById(id);
        this.clientRest.deleteUserById(id);
        return true;

    }

    @Override
    @Transactional
    public Usuario update(Usuario user, Long id) {
        Optional<Usuario> userBd = this.repository.findById(id);
        if (userBd.isEmpty()) return null;
        userBd.get().setEmail(user.getEmail());
        userBd.get().setName(user.getName());
        userBd.get().setPassword(user.getPassword());
        return userBd.get();
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existUserByEmail(String email) {
        return this.repository.existsUsuarioByEmail(email);
    }


    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAllById(List<Long> ids) {
        return this.repository.findAllById(ids);
    }
}
