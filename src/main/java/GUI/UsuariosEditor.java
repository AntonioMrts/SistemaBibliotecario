package GUI;

import DAO.UsuariosDAO;
import DB.Db;
import Logica.Usuarios;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UsuariosEditor {
    public JPanel usuariosPane;
    private JTextField id;
    private JTextField nome;
    private JTextField email;
    private JTextField cpf;
    private JTextField telefone;
    private JButton cadastrarButton;
    private JButton atualizarButton;
    private JButton deletarButton;
    private JButton salvarButton;
    private JButton listarUsuariosButton;
    private JButton limparCamposButton;
    private JTable usuariosTable;

    private enum Modo {Cadastrar, Atualizar, Deletar}
    private Modo modoAtual;

    private UsuariosDAO usuDAO;
    private Connection conexao;

    public UsuariosEditor() throws SQLException {
        this.conexao = Db.getConexao();

        Usuarios usu = new Usuarios();
        usuDAO = new UsuariosDAO();

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modoAtual = Modo.Cadastrar;
                id.setText("");
                id.setEditable(false);
                nome.setEditable(true);
                email.setEditable(true);
                cpf.setEditable(true);
                telefone.setEditable(true);
            }
        });
        atualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modoAtual = Modo.Atualizar;
                id.setText("");
                id.setEditable(true);
                nome.setEditable(true);
                email.setEditable(true);
                cpf.setEditable(true);
                telefone.setEditable(true);
            }
        });

        deletarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modoAtual = Modo.Deletar;
                id.setText("");
                id.setEditable(true);
                nome.setEditable(false);
                email.setEditable(false);
                cpf.setEditable(false);
                telefone.setEditable(false);
            }
        });

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (modoAtual) {
                    case Cadastrar:
                        if (nome.getText().isEmpty() || email.getText().isEmpty() || cpf.getText().isEmpty() || telefone.getText().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                            return;
                        }

                        usu.setNome(nome.getText());
                        usu.setEmail(email.getText());
                        usu.setCpf(cpf.getText());
                        usu.setTelefone(telefone.getText());

                        try {
                            usuDAO.adicionarUsuario(usu);
                            JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case Atualizar:
                        if (id.getText().isEmpty() || nome.getText().isEmpty() || email.getText().isEmpty() || cpf.getText().isEmpty() || telefone.getText().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                            return;
                        }

                        usu.setId_usuario(Integer.parseInt(id.getText()));
                        usu.setNome(nome.getText());
                        usu.setEmail(email.getText());
                        usu.setCpf(cpf.getText());
                        usu.setTelefone(telefone.getText());

                        try {
                            usuDAO.alterarUsuario(usu);
                            JOptionPane.showMessageDialog(null, "Usuário atualizado com sucesso!");
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case Deletar:
                        if (id.getText().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Preencha o campo do ID!");
                            return;
                        }

                        usu.setId_usuario(Integer.parseInt(id.getText()));

                        try {
                            usuDAO.removerUsuario(usu);
                            JOptionPane.showMessageDialog(null, "Usuário removido com sucesso!");
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                }
            }
        });

        listarUsuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mostrarUsuarios();
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
                email.setText("");
                cpf.setText("");
                telefone.setText("");
            }
        });
    }

    public void mostrarUsuarios() throws SQLException {

        String[] colunas = {"ID_Usuario", "Nome", "Email", "CPF", "Telefone"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        usuariosTable.setModel(model);

        List<Usuarios> usuarios = usuDAO.getListarUsuarios();

        for (Usuarios usuario : usuarios) {
            model.addRow(new Object[] {
                    usuario.getId_usuario(),
                    usuario.getNome(),
                    usuario.getEmail(),
                    usuario.getCpf(),
                    usuario.getTelefone()
            });
        }
    }
}
