package DAO;

import DB.Db;
import Logica.Livros;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LivrosDAO {
    private Connection conexao;

    public LivrosDAO() throws SQLException {
        this.conexao = Db.getConexao();
    }

    public void adicionarLivro(Livros l1) throws SQLException {
        String sql = "INSERT INTO livros (titulo, autor, editora, genero, ano, isbn, quantidade_disponivel) VALUES (?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setString(1, l1.getTitulo());
        stmt.setString(2, l1.getAutor());
        stmt.setString(3, l1.getEditora());
        stmt.setString(4, l1.getGenero());
        stmt.setInt(5, l1.getAno());
        stmt.setString(6, l1.getIsbn());
        stmt.setInt(7, l1.getQuantidade_disponivel());

        stmt.execute(); // executa a SQL com os dados definidos acima
        stmt.close(); // libera os recursos do banco
    }

    public List<Livros> getListarLivros() throws SQLException {
        String sql = "SELECT * FROM livros;";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        ResultSet res = stmt.executeQuery();
        List<Livros> listaLivros = new ArrayList<Livros>();
        while (res.next()) {
            Livros l1 = new Livros();
            l1.setId_livro(res.getInt("Id_Livro"));
            l1.setTitulo(res.getString("Titulo"));
            l1.setAutor(res.getString("Autor"));
            l1.setEditora(res.getString("Editora"));
            l1.setGenero(res.getString("Genero"));
            l1.setAno(res.getInt("Ano"));
            l1.setIsbn(res.getString("ISBN"));
            l1.setQuantidade_disponivel(res.getInt("quantidade_disponivel"));
            listaLivros.add(l1);
        }
        res.close();
        stmt.close();
        return listaLivros;
    }

    public List<Livros> getListarLivrosId(Livros l1) throws SQLException {
        String sql = "SELECT * FROM livros WHERE ID_Livro=?;";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setInt(1, l1.getId_livro());

        ResultSet res = stmt.executeQuery();
        List<Livros> listarLivrosId = new ArrayList<Livros>();

        while (res.next()) {
            l1.setId_livro(res.getInt("Id_Livro"));
            l1.setTitulo(res.getString("Titulo"));
            l1.setAutor(res.getString("Autor"));
            l1.setEditora(res.getString("Editora"));
            l1.setGenero(res.getString("Genero"));
            l1.setAno(res.getInt("Ano"));
            l1.setIsbn(res.getString("ISBN"));
            l1.setQuantidade_disponivel(res.getInt("quantidade_disponivel"));
            listarLivrosId.add(l1);
        }
        res.close();
        stmt.close();
        return listarLivrosId;
    }

    public void alterarLivros(Livros l1) throws SQLException {
        String sql = "UPDATE livros SET Titulo=?, Autor=?, Editora=?, Genero=?, Ano=?, ISBN=?, Quantidade_Disponivel=? WHERE Id_Livro=?;";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setString(1, l1.getTitulo());
        stmt.setString(2, l1.getAutor());
        stmt.setString(3, l1.getEditora());
        stmt.setString(4, l1.getGenero());
        stmt.setInt(5, l1.getAno());
        stmt.setString(6, l1.getIsbn());
        stmt.setInt(7, l1.getQuantidade_disponivel());
        stmt.setInt(8, l1.getId_livro());

        stmt.execute();
        stmt.close();
    }

    public void removerLivro(Livros l1) throws SQLException {
        String sql = "DELETE FROM livros WHERE Id_Livro=?;";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setInt(1, l1.getId_livro());

        stmt.execute();
        stmt.close();
    }
}
