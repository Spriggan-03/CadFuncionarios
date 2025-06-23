package DAO;

import Classes.Funcionario;
import util.Conexao;

import java.sql.*;

public class FuncionarioDAO {
	
	// Regra de Negócio 1: Verifica se a pessoa existe antes de cadastrar o funcionário
	private boolean pessoaExiste(int idPessoa) {
        String sql = "SELECT id FROM pessoa WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPessoa);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            System.out.println("Erro ao verificar pessoa: " + e.getMessage());
            return false;
        }
    }

    private boolean funcionarioTemProjeto(int idFuncionario) {
        String sql = "SELECT id FROM projeto WHERE id_funcionario = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idFuncionario);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            System.out.println("Erro ao verificar projetos: " + e.getMessage());
            return false;
        }
    }

    public void inserir(Funcionario funcionario) {
        if (!pessoaExiste(funcionario.getId())) {
            System.out.println("Erro: Pessoa com ID " + funcionario.getId() + " não existe.");
            return;
        }

        if (!funcionario.getMatricula().matches("F\\d{3}")) {
            System.out.println("Erro: Matrícula inválida.");
            return;
        }

        String sql = "INSERT INTO funcionario (id, matricula, departamento) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, funcionario.getId());
            stmt.setString(2, funcionario.getMatricula());
            stmt.setString(3, funcionario.getDepartamento());
            stmt.executeUpdate();
            System.out.println("Funcionário cadastrado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar funcionário: " + e.getMessage());
        }
    }

    public void excluir(int idFuncionario) {
        if (funcionarioTemProjeto(idFuncionario)) {
            System.out.println("Erro: Funcionário possui projetos vinculados e não pode ser excluído.");
            return;
        }

        String sql = "DELETE FROM funcionario WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idFuncionario);
            stmt.executeUpdate();
            System.out.println("Funcionário excluído com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao excluir funcionário: " + e.getMessage());
        }
    }
}
