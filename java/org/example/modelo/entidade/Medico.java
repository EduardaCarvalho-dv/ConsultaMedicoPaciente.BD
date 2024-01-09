package org.example.modelo.entidade;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Medico {

    private int id;
    private String nome;
    private int matricula;
    private String especialidade;
    private float salario;

}
