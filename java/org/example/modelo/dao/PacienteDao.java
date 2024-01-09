package org.example.modelo.dao;

import org.example.modelo.entidade.Medico;
import org.example.modelo.entidade.Paciente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PacienteDao extends AbstratoDao{

    public boolean adicionar(Paciente paciente){
        boolean aux;
        String sql = "insert into paciente (nome, cpf, doenca) values (?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)){
            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getCpf());
            stmt.setString(3, paciente.getDoenca());

            aux = stmt.executeUpdate() == 1;
        }catch (SQLException e){
            aux = false;
        }
        return aux;
    }

    public Paciente buscarPorCpf(String cpf){
        Paciente paciente = null;

        String sql = "select * from paciente where cpf = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)){
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                paciente = new Paciente(rs.getInt("id"), rs.getString("nome"), rs.getString("cpf"), rs.getString("doenca"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return paciente;
    }
}
