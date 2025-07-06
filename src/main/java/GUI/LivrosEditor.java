package GUI;

import DAO.LivrosDAO;
import DB.Db;
import Logica.Livros;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LivrosEditor extends JFrame {
    public JPanel livrosPanel;
    private JTextField id;
    private JTextField titulo;
    private JTextField autor;
    private JTextField editora;
    private JTextField genero;
    private JTextField ano;
    private JTextField isbn;
    private JTextField quantidade_disponivel;
    private JButton cadastra;
    private JButton atualizar;
    private JButton deletar;
    private JButton salvar;
    private JButton ListarLivros;
    private JButton LimparCampos;
    private JTable livrosTable;

    private enum Modo {Cadastrar, Atualizar, Deletar}

    private Modo modoAtual;
    private LivrosDAO livrosDAO;
    private Connection conexao;

    public LivrosEditor() throws SQLException {
        this.conexao = Db.getConexao();

        livrosDAO = new LivrosDAO();
        Livros l1 = new Livros();

//        getMostrarLivros();

        LimparCampos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                id.setText("");
                titulo.setText("");
                autor.setText("");
                editora.setText("");
                genero.setText("");
                ano.setText("");
                isbn.setText("");
                quantidade_disponivel.setText("");
            }
        });
        cadastra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modoAtual = Modo.Cadastrar;
                id.setEditable(false);
                id.setText("");
                titulo.setEditable(true);
                autor.setEditable(true);
                editora.setEditable(true);
                genero.setEditable(true);
                ano.setEditable(true);
                isbn.setEditable(true);
                quantidade_disponivel.setEditable(true);

            }
        });

        atualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modoAtual = Modo.Atualizar;
                id.setEditable(true);
                id.setText("");
                titulo.setEditable(true);
                autor.setEditable(true);
                editora.setEditable(true);
                genero.setEditable(true);
                ano.setEditable(true);
                isbn.setEditable(true);
                quantidade_disponivel.setEditable(true);
            }
        });

        deletar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modoAtual = Modo.Deletar;
                id.setEditable(true);
                id.setText("");
                titulo.setEditable(false);
                autor.setEditable(false);
                editora.setEditable(false);
                genero.setEditable(false);
                ano.setEditable(false);
                isbn.setEditable(false);
                quantidade_disponivel.setEditable(false);
            }
        });

        salvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (modoAtual) {
                    case Cadastrar:
                        if (titulo.getText().isEmpty() || autor.getText().isEmpty() || editora.getText().isEmpty() || genero.getText().isEmpty() || ano.getText().isEmpty() || isbn.getText().isEmpty() || quantidade_disponivel.getText().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                            return;
                        }

                        l1.setTitulo(titulo.getText());
                        l1.setAutor(autor.getText());
                        l1.setEditora(editora.getText());
                        l1.setGenero(genero.getText());
                        l1.setAno(Integer.parseInt(ano.getText()));
                        l1.setIsbn(isbn.getText());
                        l1.setQuantidade_disponivel(Integer.parseInt(quantidade_disponivel.getText()));

                        try {
                            livrosDAO.adicionarLivro(l1);
                            JOptionPane.showMessageDialog(null, "Livro cadastrado com sucesso!");
                            System.out.println("Cadastrado com sucesso!");
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case Atualizar:
                        if (id.getText().isEmpty() || titulo.getText().isEmpty() || autor.getText().isEmpty() || editora.getText().isEmpty() || genero.getText().isEmpty() || ano.getText().isEmpty() || isbn.getText().isEmpty() || quantidade_disponivel.getText().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                            return;
                        }

                        l1.setId_livro(Integer.parseInt(id.getText()));
                        l1.setTitulo(titulo.getText());
                        l1.setAutor(autor.getText());
                        l1.setEditora(editora.getText());
                        l1.setGenero(genero.getText());
                        l1.setAno(Integer.parseInt(ano.getText()));
                        l1.setIsbn(isbn.getText());
                        l1.setQuantidade_disponivel(Integer.parseInt(quantidade_disponivel.getText()));

                        try {
                            livrosDAO.alterarLivros(l1);
                            JOptionPane.showMessageDialog(null, "Livro atualizado com sucesso!");
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case Deletar:
                        if (id.getText().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Preencha o campo do ID!");
                            return;
                        }

                        l1.setId_livro(Integer.parseInt(id.getText()));

                        try {
                            livrosDAO.removerLivro(l1);
                            JOptionPane.showMessageDialog(null, "Livro removido com sucesso!");
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                }
            }

        });

        ListarLivros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    getMostrarLivros();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void getMostrarLivros() throws SQLException {

        String[] colunas = {"ID_Livro", "titulo", "autor", "editora", "genero", "ano", "isbn", "quantidade_disponível"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        livrosTable.setModel(model);

        List<Livros> livros = livrosDAO.getListarLivros();

        for (Livros livro : livros) {
            model.addRow(new Object[]{
                    livro.getId_livro(),
                    livro.getTitulo(),
                    livro.getAutor(),
                    livro.getEditora(),
                    livro.getGenero(),
                    livro.getAno(),
                    livro.getIsbn(),
                    livro.getQuantidade_disponivel()
            });
        }
    }

}
