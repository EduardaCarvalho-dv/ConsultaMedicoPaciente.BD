package org.example.modelo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConexaoSingleton {
    private static Connection conexao;

    private ConexaoSingleton() {
    }

    public static Connection getInstance() {
        if (conexao == null) {
            try {
                conexao = DriverManager.getConnection("jdbc:mysql://localhost/lista_05", "root", "123456");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return conexao;
    }
}
