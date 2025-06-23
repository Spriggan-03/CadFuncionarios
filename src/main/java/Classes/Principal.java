package Classes;

import DAO.PessoaDAO;
import DAO.FuncionarioDAO;
import DAO.ProjetoDAO;

import Classes.Pessoa;
import Classes.Funcionario;
import Classes.Projeto;

import java.util.List;
import java.util.Scanner;

public class Principal {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
        PessoaDAO pessoaDAO = new PessoaDAO();
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        ProjetoDAO projetoDAO = new ProjetoDAO();

        while (true) {
            System.out.println("\n--- MENU ---");
            System.out.println("1 - Cadastrar Pessoa");
            System.out.println("2 - Listar Pessoas");
            System.out.println("3 - Cadastrar Funcionário");
            System.out.println("4 - Excluir Funcionário");
            System.out.println("5 - Cadastrar Projeto");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            int op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1:
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    Pessoa p = new Pessoa(nome, email);
                    pessoaDAO.inserir(p);
                    break;

                case 2:
                    List<Pessoa> pessoas = pessoaDAO.listar();
                    for (Pessoa pessoa : pessoas) {
                        System.out.println(pessoa);
                    }
                    break;

                case 3:
                    System.out.print("ID da Pessoa já cadastrada: ");
                    int idPessoa = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Matrícula (F001): ");
                    String matricula = sc.nextLine();
                    System.out.print("Departamento: ");
                    String departamento = sc.nextLine();

                    Funcionario f = new Funcionario();
                    f.setId(idPessoa);
                    f.setMatricula(matricula);
                    f.setDepartamento(departamento);

                    funcionarioDAO.inserir(f);
                    break;

                case 4:
                    System.out.print("ID do Funcionário a excluir: ");
                    int idFunc = sc.nextInt();
                    funcionarioDAO.excluir(idFunc);
                    break;

                case 5:
                    System.out.print("Nome do Projeto: ");
                    String nomeProj = sc.nextLine();
                    System.out.print("Descrição: ");
                    String desc = sc.nextLine();
                    System.out.print("ID do Funcionário Responsável: ");
                    int idResp = sc.nextInt();

                    Projeto proj = new Projeto(nomeProj, desc, idResp);
                    projetoDAO.inserir(proj);
                    break;

                case 0:
                    System.out.println("Encerrando...");
                    sc.close();
                    return;

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

}
