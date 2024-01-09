package org.example.modelo.entidade;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Consulta {

    private LocalDateTime horario;
    private float valor;
    private Medico medico;
    private Paciente paciente;

}
