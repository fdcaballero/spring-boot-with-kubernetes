package org.fdcaballero.msvccursos.models.entity;

import lombok.*;
import org.fdcaballero.msvccursos.models.Usuario;
import org.hibernate.Hibernate;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table(name = "cursos")

public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    @NotEmpty
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curso_id")
    @ToString.Exclude
    private List<CursoUsuario> cursosUsuarios;

    @Transient
    private List<Usuario> usuarios;

    public Curso() {
        this.cursosUsuarios = new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Curso curso = (Curso) o;
        return id != null && Objects.equals(id, curso.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public  void addCursoUsuario(CursoUsuario cursoUsuario){
        this.cursosUsuarios.add(cursoUsuario);
    }

    public void removeCursoUsuario(CursoUsuario cursoUsuario) {
        this.cursosUsuarios.remove(cursoUsuario);
    }
}
