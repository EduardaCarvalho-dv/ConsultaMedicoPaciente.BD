package org.example.modelo.entidade;

import lombok.*;

@ToString
@NoArgsConstructor
@Setter
@AllArgsConstructor
@Getter
public class Paciente {

    private int id;
    private String nome;
    private String cpf;
    private String doenca;

}
