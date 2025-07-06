import GUI.*;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class Login {

    public static class TesteTela extends JFrame {

        public TesteTela() throws SQLException {
            setTitle("Editar Tabelas");
            setSize(900, 700);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(false);
            setLocationRelativeTo(null);

            JTabbedPane tabbedPane = new JTabbedPane();

            LivrosEditor telaLivros = new LivrosEditor();
            UsuariosEditor telaUsuarios = new UsuariosEditor();
            FuncionariosEditor telaFuncionarios = new FuncionariosEditor();
            EmprestimosEditor telaEmprestimos = new EmprestimosEditor();


            tabbedPane.addTab("Livros", telaLivros.livrosPanel);
            tabbedPane.addTab("Usuarios", telaUsuarios.usuariosPane);
            tabbedPane.addTab("Funcionários", telaFuncionarios.funcionariosPane);
            tabbedPane.addTab("Empréstimos", telaEmprestimos.emprestimosPane);

            add(tabbedPane, BorderLayout.CENTER);
        }

        private JPanel criarPainelTabela(String nomeTabela) {
            JPanel painel = new JPanel(new BorderLayout());
            // Aqui você pode adicionar seu JTable carregado com dados da tabela do banco
            JTable tabela = new JTable(); // configure seu modelo e dados aqui
            painel.add(new JScrollPane(tabela), BorderLayout.CENTER);
            return painel;
        }

//        public static void main(String[] args) throws SQLException {
//            SwingUtilities.invokeLater(() -> {
//                try {
//                    new GUI.TesteTela().setVisible(true);
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//            });
//        }
    }
}
