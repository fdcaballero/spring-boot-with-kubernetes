package org.fdcaballero.msusuarios.services;

import org.fdcaballero.msusuarios.models.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<Usuario> findAll();

    Optional<Usuario> findById(Long id);

    Usuario save(Usuario usuario);

    Boolean detele(Long id);

    Usuario update(Usuario user, Long id);

    Boolean existUserByEmail(String email);

    List<Usuario> findAllById(List<Long> ids);
}
