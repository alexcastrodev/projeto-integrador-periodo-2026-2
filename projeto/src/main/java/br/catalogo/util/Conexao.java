package br.catalogo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Conexao {
    private Conexao() {}

    private static String env(String k, String padrao) {
        String v = System.getenv(k);
        return v == null ? padrao : v;
    }

    public static Connection abrir() throws SQLException {
        return DriverManager.getConnection(
                env("DB_URL", "jdbc:postgresql://localhost:5432/catalogo"),
                env("DB_USER", System.getProperty("user.name")),
                env("DB_PASS", ""));
    }
}
