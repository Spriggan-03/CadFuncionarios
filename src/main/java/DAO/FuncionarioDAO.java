package DAO;

import Classes.Funcionario;
import util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO responsável pelas operações com a entidade Funcionario.
 */
public class FuncionarioDAO {

    // Verifica se a pessoa existe (para garantir integridade)
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

    // Verifica se o funcionário possui projetos vinculados
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

    // Cadastra funcionário (usando pessoa existente)
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

    // Atualiza dados de funcionário (inclui dados da tabela pessoa)
    public void atualizar(Funcionario f) {
        String sqlPessoa = "UPDATE pessoa SET nome = ?, email = ? WHERE id = ?";
        String sqlFuncionario = "UPDATE funcionario SET matricula = ?, departamento = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar()) {
            conn.setAutoCommit(false); // Transação para garantir integridade

            try (PreparedStatement stmtPessoa = conn.prepareStatement(sqlPessoa);
                 PreparedStatement stmtFunc = conn.prepareStatement(sqlFuncionario)) {

                stmtPessoa.setString(1, f.getNome());
                stmtPessoa.setString(2, f.getEmail());
                stmtPessoa.setInt(3, f.getId());
                stmtPessoa.executeUpdate();

                stmtFunc.setString(1, f.getMatricula());
                stmtFunc.setString(2, f.getDepartamento());
                stmtFunc.setInt(3, f.getId());
                stmtFunc.executeUpdate();

                conn.commit();
                System.out.println("Funcionário atualizado com sucesso!");

            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Erro na atualização. Alterações desfeitas.");
                e.printStackTrace();
            }

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar funcionário: " + e.getMessage());
        }
    }

    // Exclui funcionário (apenas se não tiver projetos vinculados)
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

    // Lista todos os funcionários (com dados da tabela pessoa)
    public List<Funcionario> listar() {
        List<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT f.*, p.nome, p.email FROM funcionario f JOIN pessoa p ON f.id = p.id";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Funcionario f = new Funcionario();
                f.setId(rs.getInt("id"));
                f.setNome(rs.getString("nome"));
                f.setEmail(rs.getString("email"));
                f.setMatricula(rs.getString("matricula"));
                f.setDepartamento(rs.getString("departamento"));
                lista.add(f);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar funcionários: " + e.getMessage());
        }

        return lista;
    }
}
