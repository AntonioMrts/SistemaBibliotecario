package Logica;

import java.util.Date;

public class Emprestimos {
    private int id_emprestimos;
    private int id_livro;
    private int id_usuario;
    private Date data_emprestimo;
    private Date data_devolucao;
    private String status;

    public void setId_emprestimos(int id_emprestimos) {
        this.id_emprestimos = id_emprestimos;
    }
    public int getId_emprestimos() {
        return id_emprestimos;
    }

    public void setId_livro(int id_livro) {
        this.id_livro = id_livro;
    }
    public int getId_livro() {
        return id_livro;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
    public int getId_usuario() {
        return id_usuario;
    }

    public void setData_emprestimo(Date data_emprestimo) {
        this.data_emprestimo = data_emprestimo;
    }
    public Date getData_emprestimo() {
        return data_emprestimo;
    }

    public void setData_devolucao(Date data_devolucao) {
        this.data_devolucao = data_devolucao;
    }
    public Date getData_devolucao() {
        return data_devolucao;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
}
