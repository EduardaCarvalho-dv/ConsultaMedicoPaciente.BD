package org.example.modelo.dao;

import org.example.modelo.entidade.Medico;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicoDao extends AbstratoDao{

    public boolean adicionar(Medico medico){
        boolean aux;
        String sql = "insert into medico (nome, matricula, especialidade, salario) values (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)){
            stmt.setString(1, medico.getNome());
            stmt.setInt(2, medico.getMatricula());
            stmt.setString(3, medico.getEspecialidade());
            stmt.setFloat(4, medico.getSalario());

            aux = stmt.executeUpdate() == 1;
        } catch (SQLException e){
            aux = false;
        }
        return aux;
    }

    public Medico buscarPorMatricula(int matricula){
        Medico medico = null;

        String sql = "select * from medico where matricula = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)){
            stmt.setInt(1, matricula);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
            medico = new Medico(rs.getInt("id"), rs.getString("nome"), rs.getInt("matricula"), rs.getString("especialidade"), rs.getFloat("salario"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return medico;
    }

}
