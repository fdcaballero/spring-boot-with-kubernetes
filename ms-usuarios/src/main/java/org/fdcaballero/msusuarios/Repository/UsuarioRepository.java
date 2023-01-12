package org.fdcaballero.msusuarios.Repository;

import org.fdcaballero.msusuarios.models.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Boolean existsUsuarioByEmail(String email);

}