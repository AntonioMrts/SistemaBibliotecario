package GUI.TelaPrincipal;

import GUI.EmprestimosEditor;
import GUI.FuncionariosEditor;
import GUI.LivrosEditor;
import GUI.UsuariosEditor;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() throws SQLException {
        setTitle("Sistema Bibliotecário");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        LivrosEditor telaLivros = new LivrosEditor();
        UsuariosEditor telaUsuarios = new UsuariosEditor();
        FuncionariosEditor telaFuncionarios = new FuncionariosEditor();
        EmprestimosEditor telaEmprestimos = new EmprestimosEditor();

        tabbedPane.addTab("Livros", telaLivros.livrosPanel);
        tabbedPane.addTab("Usuários", telaUsuarios.usuariosPane);        tabbedPane.addTab("Funcionários", telaFuncionarios.funcionariosPane);
        tabbedPane.addTab("Empréstimos", telaEmprestimos.emprestimosPane);

        add(tabbedPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new TelaPrincipal().setVisible(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
