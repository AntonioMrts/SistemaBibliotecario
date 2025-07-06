package Logica;

public class Usuarios {
    private int id_usuario;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
    public int getId_usuario() {
        return id_usuario;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getNome() {
        return nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getCpf() {
        return cpf;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getTelefone() {
        return telefone;
    }

}
