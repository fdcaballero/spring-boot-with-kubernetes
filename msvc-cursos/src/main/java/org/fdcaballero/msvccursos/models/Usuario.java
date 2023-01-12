package org.fdcaballero.msvccursos.models;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Usuario {
    private Long id;
    private String name;
    private String email;
    private String password;


}
