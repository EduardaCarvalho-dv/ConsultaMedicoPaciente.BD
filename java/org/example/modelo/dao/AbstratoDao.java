package org.example.modelo.dao;

import java.sql.Connection;

public abstract class AbstratoDao {

    protected Connection conexao;

    public AbstratoDao() {
        conexao = ConexaoSingleton.getInstance();
    }

}
