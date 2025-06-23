package DAO;

import Classes.Projeto;
import util.Conexao;

import java.sql.*;

public class ProjetoDAO {
	
	private boolean funcionarioExiste(int idFuncionario) {
        String sql = "SELECT id FROM funcionario WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idFuncionario);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            System.out.println("Erro ao verificar funcionário: " + e.getMessage());
            return false;
        }
    }

    public void inserir(Projeto projeto) {
        if (!funcionarioExiste(projeto.getIdFuncionario())) {
            System.out.println("Erro: Funcionário com ID " + projeto.getIdFuncionario() + " não existe.");
            return;
        }

        String sql = "INSERT INTO projeto (nome, descricao, id_funcionario) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, projeto.getNome());
            stmt.setString(2, projeto.getDescricao());
            stmt.setInt(3, projeto.getIdFuncionario());

            stmt.executeUpdate();
            System.out.println("Projeto cadastrado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar projeto: " + e.getMessage());
        }
    }

}
