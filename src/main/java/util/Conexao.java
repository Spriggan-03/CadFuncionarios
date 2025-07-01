package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

	// "endereço" do banco
	private static final String URL = "jdbc:mysql://localhost:3306/empresa";
    private static final String USUARIO = "root";
    private static final String SENHA = ""; // Não rolou colocar a senha

    public static Connection conectar() {
    	// Tenta executar a conexão
        try {
            return DriverManager.getConnection(URL, USUARIO, SENHA);
            // Mensagem de erro
        } catch (SQLException e) {
            System.out.println("Erro ao conectar com o banco: " + e.getMessage());
            return null;
        }
    }
	
}
