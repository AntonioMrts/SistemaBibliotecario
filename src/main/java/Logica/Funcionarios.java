package Logica;

public class Funcionarios {
    private int id_funcionario;
    private String nome;
    private String cargo;
    private String login;
    private String senha;

    public void setId_funcionario(int id_funcionario) {
        this.id_funcionario = id_funcionario;
    }
    public int getId_funcionario() {
        return id_funcionario;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getNome() {
        return nome;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    public String getCargo() {
        return cargo;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    public String getLogin() {
        return login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String getSenha() {
        return senha;
    }
}