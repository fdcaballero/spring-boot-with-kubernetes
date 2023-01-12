package org.fdcaballero.msvccursos.models.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cursos_usuarios")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CursoUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", unique = true)
    private  Long usuarioId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CursoUsuario that = (CursoUsuario) o;
        return usuarioId != null && Objects.equals(usuarioId, that.usuarioId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
