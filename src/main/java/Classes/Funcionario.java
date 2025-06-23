package Classes;

public class Funcionario extends Pessoa {
	private String matricula;     // Ex: F001
    private String departamento;  // Ex: TI, RH, etc.

    public Funcionario() {}

    public Funcionario(String nome, String email, String matricula, String departamento) {
        super(nome, email); // Chama o construtor da classe Pessoa
        this.matricula = matricula;
        this.departamento = departamento;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        if (matricula.matches("F\\d{3}")) {
            this.matricula = matricula;
        } else {
            System.out.println("Matrícula inválida! Use o formato F001, F123, etc.");
        }
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    @Override
    public String toString() {
        return "Funcionario [id=" + getId() + ", nome=" + getNome() +
                ", email=" + getEmail() + ", matricula=" + matricula +
                ", departamento=" + departamento + "]";
    }

}
