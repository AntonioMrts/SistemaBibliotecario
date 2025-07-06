package GUI;

import DB.Db;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BooksEditor extends JFrame {
    private JPanel contentPane;
    private JTextField titleBookField;
    private JTextField authorBookField;
    private JTextField editoraBookField;
    private JTextField generoBookField;
    private JTextField anoBookField;
    private JTextField isbnBookField;
    private JTextField qtdBookField;
    private JButton salvarButton;
    private JTable booksTable;

    private Connection conexao;

    public BooksEditor() throws SQLException {

        this.conexao = Db.getConexao();
        setContentPane(contentPane);
        setTitle("Cadastro de Livros");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mostrarLivro();
        getMostrarLivros();
    }

//    public void configurarEventos() {
//        saveButton.addActionListener(e -> {
//            String senha = new String(Teste.getPassword());
//            System.out.println("Senha: " + senha);
//        });
//    }

    public void mostrarLivro() {
        salvarButton.addActionListener(e -> {
            String titulo = titleBookField.getText();
            String autor = authorBookField.getText();
            String editora = editoraBookField.getText();
            String genero = generoBookField.getText();
            int ano = Integer.parseInt(anoBookField.getText());
            String isbn = isbnBookField.getText();
            int disponivel = Integer.parseInt(qtdBookField.getText());

            System.out.println("Titulo: " + titulo);
            System.out.println("Autor: " + autor);
            System.out.println("Editora: " + editora);
            System.out.println("Genero: " + genero);
            System.out.println("Ano: " + ano);
            System.out.println("ISBN: " + isbn);
            System.out.println("Quantidade disponível: " + disponivel);
        });
    }

    public void getMostrarLivros() throws SQLException {
        String sql = "SELECT * FROM livros";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        ResultSet res = stmt.executeQuery();

        String[] colunas = {"ID_Livro", "titulo", "autor", "editora", "genero", "ano", "isbn", "quantidade_disponível"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        booksTable.setModel(model);

        while (res.next()) {
            int id = res.getInt("ID_Livro");
            String titulo = res.getString("titulo");
            String autor = res.getString("autor");
            String editora = res.getString("editora");
            int ano = res.getInt("ano");
            String isbn = res.getString("isbn");
            int quantidade_disponivel = res.getInt("quantidade_disponivel");

            model.addRow(new Object[]{id, titulo, autor, editora, ano, isbn, quantidade_disponivel});
        }

        stmt.close();
        res.close();
    }

}
