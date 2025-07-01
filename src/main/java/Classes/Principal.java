package Classes;

import DAO.PessoaDAO;
import DAO.FuncionarioDAO;
import DAO.ProjetoDAO;

import java.util.List;
import java.util.Scanner;

public class Principal {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		// DAO's para acesso ao banco
        PessoaDAO pessoaDAO = new PessoaDAO();
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        ProjetoDAO projetoDAO = new ProjetoDAO();

        // Laço para fazer menu e escolha de opções para prosseguir
        while (true) {
        	System.out.println("\n--- MENU ---");

        	// CRUD Pessoa
        	System.out.println("1 - Cadastrar Pessoa");
        	System.out.println("2 - Listar Pessoas");
        	System.out.println("3 - Atualizar Pessoa");
        	System.out.println("4 - Excluir Pessoa");

        	// CRUD Funcionário
        	System.out.println("5 - Cadastrar Funcionário");
        	System.out.println("6 - Listar Funcionários");
        	System.out.println("7 - Atualizar Funcionário");
        	System.out.println("8 - Excluir Funcionário");

        	// CRUD Projeto
        	System.out.println("9 - Cadastrar Projeto");
        	System.out.println("10 - Atualizar Projeto");
        	System.out.println("11 - Excluir Projeto");

        	// Sair
        	System.out.println("0 - Sair");
        	System.out.print("Escolha: ");
            int op = sc.nextInt();
            sc.nextLine();

            switch (op) {

            // ===== CRUD Pessoa =====
            case 1:
                // Cadastro de pessoa simples
                System.out.print("Nome: ");
                String nome = sc.nextLine();
                System.out.print("Email: ");
                String email = sc.nextLine();
                Pessoa p = new Pessoa(nome, email);
                pessoaDAO.inserir(p);
                break;

                
            case 2:
                // Listagem de todas as pessoas cadastradas
                List<Pessoa> pessoas = pessoaDAO.listar();
                for (Pessoa pessoa : pessoas) {
                    System.out.println(pessoa);
                }
                break;

                
            case 3:
                // Atualização de pessoa (nome e email)
                System.out.print("ID da Pessoa a atualizar: ");
                int idPessoaUpdate = sc.nextInt();
                sc.nextLine();
                System.out.print("Novo Nome: ");
                String novoNome = sc.nextLine();
                System.out.print("Novo Email: ");
                String novoEmail = sc.nextLine();
                Pessoa pessoaAtualizada = new Pessoa(novoNome, novoEmail);
                pessoaAtualizada.setId(idPessoaUpdate);
                pessoaDAO.atualizar(pessoaAtualizada);
                break;

                
            case 4:
                // Exclusão de pessoa pelo ID
                System.out.print("ID da Pessoa a excluir: ");
                int idExcluirPessoa = sc.nextInt();
                pessoaDAO.excluir(idExcluirPessoa);
                break;

                
                
            // ===== CRUD Funcionário =====
            case 5:
                // Cadastro de funcionário baseado em uma pessoa existente
                System.out.print("ID da Pessoa já cadastrada: ");
                int idPessoa = sc.nextInt();
                sc.nextLine();
                System.out.print("Matrícula (F001): ");
                String matricula = sc.nextLine();
                System.out.print("Departamento: ");
                String departamento = sc.nextLine();

                Funcionario f = new Funcionario();
                f.setId(idPessoa); // Usa o ID da pessoa existente (necessário cadastrar a pessoa antes)
                f.setMatricula(matricula);
                f.setDepartamento(departamento);

                funcionarioDAO.inserir(f);
                break;

                
            case 6:
                // Listagem de todos os funcionários cadastrados
                List<Funcionario> funcionarios = funcionarioDAO.listar();
                for (Funcionario func : funcionarios) {
                    System.out.println(func);
                }
                break;

                
            case 7:
                // Atualização de funcionário (inclui dados da pessoa)
                System.out.print("ID do Funcionário a atualizar: ");
                int idFuncUpdate = sc.nextInt();
                sc.nextLine();
                System.out.print("Novo Nome: ");
                String nomeFunc = sc.nextLine();
                System.out.print("Novo Email: ");
                String emailFunc = sc.nextLine();
                System.out.print("Nova Matrícula (F001): ");
                String matFunc = sc.nextLine();
                System.out.print("Novo Departamento: ");
                String depFunc = sc.nextLine();

                Funcionario funcAtualizado = new Funcionario(nomeFunc, emailFunc, matFunc, depFunc);
                funcAtualizado.setId(idFuncUpdate);
                funcionarioDAO.atualizar(funcAtualizado);
                break;

                
            case 8:
                // Exclusão de funcionário pelo ID
                System.out.print("ID do Funcionário a excluir: ");
                int idFunc = sc.nextInt();
                funcionarioDAO.excluir(idFunc);
                break;

                
                
            // ===== CRUD Projeto =====
            case 9:
                // Cadastro de um novo projeto
                System.out.print("Nome do Projeto: ");
                String nomeProj = sc.nextLine();
                System.out.print("Descrição: ");
                String desc = sc.nextLine();
                System.out.print("ID do Funcionário Responsável: ");
                int idResp = sc.nextInt();

                Projeto proj = new Projeto(nomeProj, desc, idResp);
                projetoDAO.inserir(proj);
                break;

                
            case 10:
                // Atualização de projeto
                System.out.print("ID do Projeto a atualizar: ");
                int idProjUpdate = sc.nextInt();
                sc.nextLine();
                System.out.print("Novo Nome do Projeto: ");
                String nomeProjUpdate = sc.nextLine();
                System.out.print("Nova Descrição: ");
                String descProjUpdate = sc.nextLine();
                System.out.print("Novo ID do Funcionário Responsável: ");
                int novoIdFunc = sc.nextInt();

                Projeto projetoAtualizado = new Projeto(nomeProjUpdate, descProjUpdate, novoIdFunc);
                projetoAtualizado.setId(idProjUpdate);
                projetoDAO.atualizar(projetoAtualizado);
                break;

                
            case 11:
                // Exclusão de projeto pelo ID
                System.out.print("ID do Projeto a excluir: ");
                int idExcluirProj = sc.nextInt();
                projetoDAO.excluir(idExcluirProj);
                break;

                
            // ===== Outros =====
            case 0:
                // Encerra o programa
                System.out.println("Encerrando...");
                sc.close();
                return;

            default:
                // Caso selecione qualquer opção que não esteja citada acima
                System.out.println("Opção inválida!");
        }

        }
    }

}
