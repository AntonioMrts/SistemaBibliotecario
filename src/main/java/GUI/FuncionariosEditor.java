package GUI;

import DAO.FuncionariosDAO;
import DB.Db;
import Logica.Funcionarios;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class FuncionariosEditor {
    public JPanel funcionariosPane;
    private JTextField id;
    private JTextField nome;
    private JTextField cargo;
    private JTextField login;
    private JPasswordField senha;
    private JButton cadastrarButton;
    private JButton atualizarButton;
    private JButton deletarButton;
    private JButton salvarButton;
    private JButton listarFuncionariosButton;
    private JButton limparCamposButton;
    private JTable funcionariosTable;

    private enum Modo {Cadastrar, Atualizar, Deletar};
    private Modo modoAtual;
    private Connection conexao;
    private FuncionariosDAO f1DAO;

    public FuncionariosEditor() throws SQLException {
        this.conexao = Db.getConexao();
        Funcionarios f1 = new Funcionarios();
        f1DAO = new FuncionariosDAO();

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modoAtual = Modo.Cadastrar;
                id.setText("");
                id.setEditable(false);
                nome.setEditable(true);
                cargo.setEditable(true);
                login.setEditable(true);
                senha.setEditable(true);
            }
        });

        atualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modoAtual = Modo.Atualizar;
                id.setText("");
                id.setEditable(true);
                nome.setEditable(true);
                cargo.setEditable(true);
                login.setEditable(true);
                senha.setEditable(true);
            }
        });

        deletarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modoAtual = Modo.Deletar;
                id.setText("");
                id.setEditable(true);
                nome.setEditable(false);
                cargo.setEditable(false);
                login.setEditable(false);
                senha.setEditable(false);
            }
        });

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (modoAtual) {
                    case Cadastrar:
                        if (nome.getText().isEmpty() || cargo.getText().isEmpty() || login.getText().isEmpty() || new String(senha.getPassword()).isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                            return;
                        }

                        f1.setNome(nome.getText());
                        f1.setCargo(cargo.getText());
                        f1.setLogin(login.getText());
                        f1.setSenha(new String(senha.getPassword()));

                        try {
                            f1DAO.adiconarFuncionario(f1);
                            JOptionPane.showMessageDialog(null, "Funcionário cadastrado com sucesso!");
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case Atualizar:
                        if (id.getText().isEmpty() || nome.getText().isEmpty() || cargo.getText().isEmpty() || login.getText().isEmpty() || new String(senha.getPassword()).isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                            return;
                        }

                        f1.setId_funcionario(Integer.parseInt(id.getText()));
                        f1.setNome(nome.getText());
                        f1.setCargo(cargo.getText());
                        f1.setLogin(login.getText());
                        f1.setSenha(new String(senha.getPassword()));

                        try {
                            f1DAO.alterarFuncionario(f1);
                            JOptionPane.showMessageDialog(null, "Funcionário atualizado com sucesso!");
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case Deletar:
                        if (id.getText().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Preencha o campo do ID!");
                            return;
                        }

                        f1.setId_funcionario(Integer.parseInt(id.getText()));

                        try {
                            f1DAO.removerFuncionario(f1);
                            JOptionPane.showMessageDialog(null, "Funcionário removido com sucesso!");
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                }
            }
        });

        limparCamposButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                id.setText("");
                nome.setText("");
                cargo.setText("");
                login.setText("");
                senha.setText("");
            }
        });
        listarFuncionariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mostrarFuncionarios();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        limparCamposButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                id.setText("");
                nome.setText("");
                cargo.setText("");
                login.setText("");
                senha.setText("");
            }
        });
    }

    public void mostrarFuncionarios() throws SQLException {
        String[] colunas = {"id_funcionario", "nome", "cargo", "login", "senha"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        funcionariosTable.setModel(model);

        List<Funcionarios> funcionarios = f1DAO.getListarFuncionarios();

        for (Funcionarios funcionario : funcionarios) {
            model.addRow(new Object[] {
                    funcionario.getId_funcionario(),
                    funcionario.getNome(),
                    funcionario.getCargo(),
                    funcionario.getLogin(),
                    funcionario.getSenha()
            });
        }
    }
}
