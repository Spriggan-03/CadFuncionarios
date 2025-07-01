package DAO;

import Classes.Projeto;
import util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO responsável pelas operações com a entidade Projeto.
 */
public class ProjetoDAO {

    // Verifica se o funcionário existe
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

    // Cadastra projeto
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

    // Atualiza projeto
    public void atualizar(Projeto p) {
        String sql = "UPDATE projeto SET nome = ?, descricao = ?, id_funcionario = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getDescricao());
            stmt.setInt(3, p.getIdFuncionario());
            stmt.setInt(4, p.getId());
            stmt.executeUpdate();
            System.out.println("Projeto atualizado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar projeto: " + e.getMessage());
        }
    }

    // Exclui projeto por ID
    public void excluir(int id) {
        String sql = "DELETE FROM projeto WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Projeto excluído com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao excluir projeto: " + e.getMessage());
        }
    }

    // Lista todos os projetos
    public List<Projeto> listar() {
        List<Projeto> lista = new ArrayList<>();
        String sql = "SELECT * FROM projeto";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Projeto p = new Projeto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descricao"));
                p.setIdFuncionario(rs.getInt("id_funcionario"));
                lista.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar projetos: " + e.getMessage());
        }

        return lista;
    }
}
