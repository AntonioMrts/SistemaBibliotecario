package DAO;

import DB.Db;
import Logica.Emprestimos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmprestimosDAO {

    private Connection conexao;

    public EmprestimosDAO() throws SQLException {
        this.conexao = Db.getConexao();
    }

    public void adicionarEmprestimo(Emprestimos e) throws SQLException {
        String sql = "INSERT INTO emprestimos (id_livro, id_usuario, data_emprestimo, data_devolucao, status) VALUES (?, ?, ?, ?, ?);";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setInt(1, e.getId_livro());
        stmt.setInt(2, e.getId_usuario());
        stmt.setDate(3, new Date(e.getData_emprestimo().getTime()));
        stmt.setDate(4, new Date(e.getData_devolucao().getTime()));
        stmt.setString(5, e.getStatus());

        stmt.execute();
        stmt.close();
    }

    public List<Emprestimos> getListarEmprestimos() throws SQLException {
        String sql = "SELECT * FROM emprestimos;";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        ResultSet res = stmt.executeQuery();
        List<Emprestimos> emprestimos = new ArrayList<>();

        while (res.next()) {
            Emprestimos e1 = new Emprestimos();

            e1.setId_emprestimos(res.getInt("id_emprestimos"));
            e1.setId_livro(res.getInt("id_livro"));
            e1.setId_usuario(res.getInt("id_usuario"));
            e1.setData_emprestimo(res.getDate("data_emprestimo"));
            e1.setData_devolucao(res.getDate("data_devolucao"));
            e1.setStatus(res.getString("status"));

            emprestimos.add(e1);
        }

        stmt.close();
        res.close();
        return emprestimos;
    }

    public void atualizarEmprestimo(Emprestimos e) throws SQLException {
        String sql = "UPDATE emprestimos SET id_livro=?, id_usuario=?, data_emprestimo=?, data_devolucao=?, status=? WHERE id_emprestimos=?;";
        PreparedStatement stmt = conexao.prepareStatement(sql);

        stmt.setInt(1, e.getId_livro());
        stmt.setInt(2, e.getId_usuario());
        stmt.setDate(3, new Date(e.getData_emprestimo().getTime()));
        stmt.setDate(4, new Date(e.getData_devolucao().getTime()));
        stmt.setString(5, e.getStatus());
        stmt.setInt(6, e.getId_emprestimos());

        stmt.execute();
        stmt.close();
    }

    public void removerEmprestimo(Emprestimos e) throws SQLException{
        String sql = "DELETE FROM emprestimos WHERE id_emprestimos=?;";
        PreparedStatement stmt = conexao.prepareStatement(sql);

        stmt.setInt(1, e.getId_emprestimos());

        stmt.execute();
        stmt.close();
    }

    public int buscarQuantidadeLivros(int idLivro) throws SQLException{
        String sql = "SELECT quantidade_disponivel FROM livros WHERE ID_Livro=?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setInt(1, idLivro);

        ResultSet res = stmt.executeQuery();
        int quantidade = 0;
        if (res.next()) {
            quantidade = res.getInt("quantidade_disponivel");
        }

        stmt.close();
        res.close();
        return quantidade;
    }

    public void reduzirQuantidadeLivros(int idLivro) throws SQLException {
        String sql = "UPDATE livros SET quantidade_disponivel = quantidade_disponivel - 1 WHERE ID_Livro=? AND quantidade_disponivel > 0;";
        PreparedStatement stmt = conexao.prepareStatement(sql);

        stmt.setInt(1, idLivro);
        stmt.executeUpdate();

        stmt.close();
    }

    public void aumentarQuantidadeLivros(int idLivro, int novaQuantidade) throws SQLException {
        String sql = "UPDATE livros SET quantidade_disponivel=? WHERE ID_Livro=?;";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setInt(1, novaQuantidade);
        stmt.setInt(2, idLivro);

        stmt.executeUpdate();
        stmt.close();
    }

    public void registrarDevolucao(int idEmprestimo) throws SQLException {
        try {
            // Descobrir qual livro está associado ao empréstimo
            String sqlLivro = "SELECT id_livro FROM emprestimos WHERE id_emprestimos=?;";
            PreparedStatement stmtLivro = conexao.prepareStatement(sqlLivro);
            stmtLivro.setInt(1, idEmprestimo);
            ResultSet res = stmtLivro.executeQuery();

            int idLivro;
            if (res.next()) {
                idLivro = res.getInt("id_livro");
            } else {
                throw new SQLException("Empréstimo não encontrado.");
            }

            // Atualizar status do empréstimo para 'Devolvido' e data de devolução
            String sqlEmprestimo = "UPDATE emprestimos SET status='Devolvido', data_devolucao = CURDATE() WHERE id_emprestimos=?;";
            PreparedStatement stmtEmprestimo = conexao.prepareStatement(sqlEmprestimo);
            stmtEmprestimo.setInt(1, idEmprestimo);
            stmtEmprestimo.executeUpdate();

            // Incrementar quantidade do livro
            String sqlLivroUpdate = "UPDATE livros SET quantidade_disponivel = quantidade_disponivel + 1 WHERE id_livro=?;";
            PreparedStatement stmtLivroUpdate = conexao.prepareStatement(sqlLivroUpdate);
            stmtLivroUpdate.setInt(1, idLivro);
            stmtLivroUpdate.executeUpdate();

            stmtLivro.close();
            stmtEmprestimo.close();
            stmtLivroUpdate.close();
        } catch (SQLException ex) {
            conexao.rollback();
            throw ex;
        }
    }
}
