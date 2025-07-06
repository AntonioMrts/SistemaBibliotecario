package GUI;

import DB.Db;
import GUI.TelaPrincipal.TelaPrincipal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TelaLogin extends JFrame{
    public JPanel loginPane;
    private JTextField login;
    private JPasswordField senha;
    private JButton entrar;


    public TelaLogin() {        setTitle("Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setContentPane(loginPane);
        login.setPreferredSize(new Dimension(200, 20));

        entrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = login.getText();
                String senhaDigitada = new String(senha.getPassword());

                if (autenticarUsuario(usuario, senhaDigitada)) {
                    JOptionPane.showMessageDialog(null, "Login bem-sucedido!");
                    dispose(); // fechar tela de login

                    try {
                        new TelaPrincipal().setVisible(true);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Usuário ou senha inválidos!");
                }
            }
        });
    }

    public boolean autenticarUsuario(String usuario, String senha) {
        boolean autenticado = false;

        try {
            Connection conexao = Db.getConexao();
            String sql = "SELECT * FROM funcionarios WHERE login=? AND senha=?";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, usuario);
            stmt.setString(2, senha);
            ResultSet res = stmt.executeQuery();


            if (res.next()) {
                autenticado = true; // encontrou um registro que bate
            }

            stmt.close();
            res.close();
            conexao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return autenticado;
    }
}
