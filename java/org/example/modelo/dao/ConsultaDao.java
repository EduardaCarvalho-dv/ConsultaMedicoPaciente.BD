package org.example.modelo.dao;

import org.example.modelo.entidade.Consulta;
import org.example.modelo.entidade.Medico;
import org.example.modelo.entidade.Paciente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDao extends AbstratoDao {

    public boolean adicionar(LocalDateTime horario, float valor, String cpf, int matricula){
        boolean aux;
        String sql = "insert into consulta (horario, valor, id_paciente, id_medico) values" +
                "(?, ?, (select id from paciente where cpf = ?), (select id from medico where matricula = ?))";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)){
            stmt.setObject(1, horario);
            stmt.setFloat(2, valor);
            stmt.setString(3, cpf);
            stmt.setInt(4, matricula);

            aux = stmt.executeUpdate() == 1;
        }catch (SQLException e){
            aux = false;
            e.printStackTrace();
        }
        return aux;
    }

    public boolean adicionar(Consulta consulta, Paciente paciente, Medico medico){
        boolean aux;
        String sql = "insert into consulta (horario, valor, id_paciente, id_medico) values" +
                "(?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)){
            stmt.setObject(1, consulta.getHorario());
            stmt.setFloat(2, consulta.getValor());
            stmt.setInt(3, paciente.getId());
            stmt.setInt(4, medico.getId());

            aux = stmt.executeUpdate() == 1;
        } catch (SQLException e){
            aux = false;
        }
        return aux;
    }

    public List<Medico> buscarMedico(String cpf){
        List<Medico> medicos = new ArrayList<>();

        String sql = "select m.* from paciente p inner join consulta c on p.id = c.id_paciente " +
                "inner join medico m on m.id = c.id_medico where p.cpf = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)){
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                Medico medico = new Medico(rs.getInt("id"), rs.getString("nome"), rs.getInt("matricula"), rs.getString("especialidade"), rs.getFloat("salario"));
                medicos.add(medico);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return medicos;
    }

    public List<Paciente> buscarPaciente(int matricula){
        List<Paciente> pacientes = new ArrayList<>();

        String sql = "select p.* from paciente p inner join consulta c on p.id = c.id_paciente " +
                "inner join medico m on m.id = c.id_medico where m.matricula = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)){
            stmt.setInt(1, matricula);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                Paciente paciente = new Paciente(rs.getInt("id"), rs.getString("nome"), rs.getString("cpf"), rs.getString("doenca"));
                pacientes.add(paciente);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return pacientes;
    }

    public boolean remover(String cpf, int matricula){
        boolean aux;
        String sql = "delete from consulta where id_paciente = (select id from paciente where cpf = ?) " +
                "and id_medico = (select id from medico where matricula = ?))";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)){
            stmt.setString(1, cpf);
            stmt.setInt(2, matricula);

            aux = stmt.executeUpdate() == 1;
        }catch (SQLException e){
            aux = false;
        }
        return aux;
    }

    public boolean remover(Paciente paciente, Medico medico){
        boolean aux;
        String sql = "delete from consulta where id_paciente = ? and id_medico = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)){
            stmt.setInt(1, paciente.getId());
            stmt.setInt(2, medico.getId());

            aux = stmt.executeUpdate() == 1;
        }catch (SQLException e){
            aux = false;
        }
        return aux;
    }

    public boolean verificar(Medico medico, Paciente paciente, Consulta consulta){
        boolean aux = false;
        String sql = "select count(*) from consulta where id_paciente = (select id from paciente where cpf = ?) and " +
                "id_medico = (select id from medico where matricula = ?) and horario = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()){
            stmt.setString(1, paciente.getCpf());
            stmt.setInt(2, medico.getMatricula());
            stmt.setObject(3, consulta.getHorario());

            if (rs.next()){
                int cont = rs.getInt(1);
                aux = cont > 0;
            }
        }catch (SQLException e){
            System.err.println("Erro ao verificar!" + e.getMessage());
        }
        return aux;
    }

    public boolean atualizarConsulta(Paciente paciente, Medico medico, Consulta consulta, LocalDateTime nHorario){
        boolean aux;
        String sql = "update consulta set horario = ? where id_paciente = (select id from paciente where cpf = ?) and " +
                "id_medico = (select id from medico where matricula = ?) and horario = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)){
            stmt.setObject(1, nHorario);
            stmt.setString(2, paciente.getCpf());
            stmt.setInt(3, medico.getMatricula());
            stmt.setObject(4, consulta.getHorario());

            aux = stmt.executeUpdate() == 1;
        }catch (SQLException e){
            aux = false;
        }
        return aux;
    }

    public List<Consulta> listarConsulta(){
        List<Consulta> consultas = new ArrayList<>();
        String sql = "select p.*, m.*, c.horario, c.valor from consulta c inner join paciente p on" +
                " p.id = c.id_paciente inner join medico m on m.id = c.id_medico order by c.horario desc";

        try (PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery() ){
            while (rs.next()){
                Paciente p = new Paciente(rs.getInt("p.id"), rs.getString("p.nome"), rs.getString("p.cpf"), rs.getString("p.doenca"));
                Medico m = new Medico(rs.getInt("m.id"), rs.getString("m.nome"), rs.getInt("m.matricula"), rs.getString("m.especialidade"), rs.getFloat("m.salario"));
                Consulta c = new Consulta(rs.getObject("c.horario", LocalDateTime.class), rs.getFloat("c.valor"), m, p);

                consultas.add(c);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return consultas;
    }
}