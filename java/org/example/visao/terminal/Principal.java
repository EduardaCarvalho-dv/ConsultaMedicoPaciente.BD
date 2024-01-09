package org.example.visao.terminal;

import org.example.modelo.dao.ConsultaDao;
import org.example.modelo.dao.MedicoDao;
import org.example.modelo.dao.PacienteDao;
import org.example.modelo.entidade.Consulta;
import org.example.modelo.entidade.Medico;
import org.example.modelo.entidade.Paciente;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Principal {

    public static void main(String[] args) throws SQLException {
        var leitor = new Scanner(System.in);

        while (true){
            System.out.println("-----------------------");
            System.out.println("1. Sair do programa");
            System.out.println("2. Cadastrar novo médico");
            System.out.println("3. Cadastrar novo paciente");
            System.out.println("4. Buscar médico por matrícula");
            System.out.println("5. Buscar paciente por CPF");
            System.out.println("6. Cadastrar uma nova consulta");
            System.out.println("7. Remover uma consulta cadastrada");
            System.out.println("8. Atualizar o horário de uma consulta cadastrada");
            System.out.println("9. Gerar relatório de consultas");
            System.out.print("Escolha sua opção: ");
            int opcao = leitor.nextInt();
            leitor.nextLine();

            if(opcao == 1){
                System.out.println("Encerrando programa!");
                break;
            } else if (opcao == 2) {
                Medico medico = new Medico();

                System.out.println("Informe o nome: ");
                medico.setNome(leitor.nextLine());

                System.out.println("Infome a matrícula: ");
                medico.setMatricula(leitor.nextInt());

                System.out.println("Informe a especialida: ");
                medico.setEspecialidade(leitor.nextLine());

                System.out.println("Informe o salario: ");
                medico.setSalario(leitor.nextFloat());

                if (new MedicoDao().adicionar(medico)){
                    System.out.println("Médico adicinado");
                } else {
                    System.out.println("Erro ao adicionar medico!");
                }
            } else if (opcao == 3) {
                Paciente paciente = new Paciente();

                System.out.println("Informe o nome do paciente: ");
                paciente.setNome(leitor.nextLine());

                System.out.println("Informe o CPF: ");
                paciente.setCpf(leitor.nextLine());

                System.out.println("Informe a doença: ");
                paciente.setDoenca(leitor.nextLine());

                if (new PacienteDao().adicionar(paciente)){
                    System.out.println("Paciente adicionado");
                } else {
                    System.out.println("Erro ao adicionar paciente!");
                }
            } else if (opcao == 4) {
                System.out.println("Digite a matrícula: ");
                var matricula = leitor.nextInt();

                Medico m = new MedicoDao().buscarPorMatricula(matricula);

                if(m == null){
                    System.out.println("Matricula não encontrada!");
                } else {
                    System.out.println(m);
                }
            } else if (opcao == 5) {
                System.out.println("Digite o cpf: ");
                var cpf = leitor.nextLine();

                Paciente p = new PacienteDao().buscarPorCpf(cpf);

                if (p == null){
                    System.out.println("CPF não encontrado!");
                } else {
                    System.out.println(p);
                }
            } else if (opcao == 6) {
                System.out.println("Digite o CPF do paciente: ");
                var cpf = leitor.nextLine();

                Paciente p = new PacienteDao().buscarPorCpf(cpf);

                if (p == null){
                    System.out.println("CPF não encontrado!");
                } else {
                    System.out.println("Digite a matricula do médico: ");
                    var matricula = leitor.nextInt();

                    Medico m = new MedicoDao().buscarPorMatricula(matricula);

                    if (m == null){
                        System.out.println("Matricula não encontada!");
                    } else {

                        Consulta c = new Consulta();

                        System.out.println("Informe o horário da consulta: ");
                        LocalDateTime horario = DateUtil.stringToDate(leitor.nextLine(), LocalDateTime.class);

                        System.out.println("Informe o valor da consulta: ");
                        var valor = leitor.nextFloat();

                        if (new ConsultaDao().adicionar(c, p, m)){
                            System.out.println("Consulta agendada!");
                        } else {
                            System.out.println("Consulta não agendada");
                        }

                    }
                }
            } else if (opcao == 7) {
                System.out.println("Digite o CPF do paciente: ");
                var cpf = leitor.nextLine();

                Paciente p = new PacienteDao().buscarPorCpf(cpf);

                if (p == null){
                    System.out.println("CPF não encontrado!");
                }else {
                    System.out.println("Digire a matrícula do médico: ");
                    var matricula = leitor.nextInt();

                    Medico m = new MedicoDao().buscarPorMatricula(matricula);

                    if (m == null){
                        System.out.println("Matrícula não encontrada!");
                    }else {
                        System.out.println("Médico encontrado");

                        if (new ConsultaDao().remover(p, m)){
                            System.out.println("Consulta removida");
                        }else {
                            System.out.println("Erro ao remover consulta!");
                        }
                    }
                }
            } else if (opcao == 8) {
                System.out.println("Digite o CPF do paciente: ");
                var cpf = leitor.nextLine();

                Paciente p = new PacienteDao().buscarPorCpf(cpf);

                if (p == null){
                    System.out.println("CPF não encontrado!");
                } else {
                    System.out.println("Digire a matrícula do médico: ");
                    var matricula = leitor.nextInt();

                    Medico m = new MedicoDao().buscarPorMatricula(matricula);

                    if (m == null){
                        System.out.println("Matrícula não encontrada!");
                    }else {
                        Consulta c = new Consulta();

                        System.out.println("Digite o horario da consulta: ");
                        c.setHorario(DateUtil.stringToDate(leitor.nextLine(), LocalDateTime.class));

                        if (new ConsultaDao().verificar(m, p, c)){
                            System.out.println("Consulta encontrada!");

                            System.out.println("Digite o horario atualizado da consulta: ");
                            LocalDateTime nHorario = DateUtil.stringToDate(leitor.nextLine(), LocalDateTime.class);

                            if (new ConsultaDao().atualizarConsulta(p, m, c, nHorario)){
                                System.out.println("Consulta Atualizada!");
                            }else {
                                System.err.println("Erro ao atualizar!");
                            }

                        } else {
                            System.out.println("Consulta não encontrada!");
                        }
                    }
                }
            } else if (opcao == 9) {
                var consultas = new ConsultaDao().listarConsulta();

                if (consultas.isEmpty()){
                    System.out.println("Não há consultas agendadas.");
                }else {
                    consultas.forEach(System.out::println);
                }
            }else {
                System.out.println("Opção inválida, tente novamente!");
            }
        }

    }

}
