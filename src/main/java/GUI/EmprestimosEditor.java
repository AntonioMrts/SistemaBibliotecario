package GUI;

import DAO.EmprestimosDAO;
import Logica.Emprestimos;

import javax.sql.rowset.RowSetWarning;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class EmprestimosEditor {
    public JPanel emprestimosPane;
    private JTextField id;
    private JTextField idLivro;
    private JTextField idUsuario;
    private JTextField dataEmprestimo;
    private JTextField dataDevolucao;
    private JTextField status;
    private JButton cadastrarButton;
    private JButton atualizarButton;
    private JButton deletarButton;
    private JButton salvarButton;
    private JButton listarEmprestimosButton;
    private JButton limparCamposButton;
    private JTable emprestimosTable;
    private JButton registrarEmprestimo;
    private JButton registrarDevolucao;

    private enum Modo {Cadastrar, Atualizar, Deletar}
    private Modo modoAtual;

    private Connection conexao;
    private EmprestimosDAO emDAO;

    public EmprestimosEditor() throws SQLException {

        Emprestimos e1 = new Emprestimos();
        emDAO = new EmprestimosDAO();

        LocalDate atual = LocalDate.now();
        LocalDate devolucao = atual.plusDays(7);
        String dataAtual = atual.toString();
        String dataDevolucaoStr = devolucao.toString();

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modoAtual = Modo.Cadastrar;
                id.setText("");
                dataEmprestimo.setText(dataAtual);
                dataDevolucao.setText(dataDevolucaoStr);
                status.setText("Pendente");
                id.setEditable(false);
                idLivro.setEditable(true);
                idUsuario.setEditable(true);
                dataEmprestimo.setEditable(false);
                dataDevolucao.setEditable(false);
                status.setEditable(false);
            }
        });

        atualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modoAtual = Modo.Atualizar;
                id.setText("");
                dataEmprestimo.setText(dataAtual);
                dataDevolucao.setText(dataDevolucaoStr);
                status.setText("Pendente");
                id.setEditable(true);
                idLivro.setEditable(true);
                idUsuario.setEditable(true);
                dataEmprestimo.setEditable(true);
                dataDevolucao.setEditable(true);
                status.setEditable(true);
            }
        });

        deletarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modoAtual = Modo.Deletar;
                id.setText("");
                id.setEditable(true);
                idLivro.setEditable(false);
                idUsuario.setEditable(false);
                dataEmprestimo.setEditable(false);
                dataDevolucao.setEditable(false);
                status.setEditable(false);
            }
        });

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                switch (modoAtual) {

                    case Cadastrar:
                        LocalDate localDateEmprestimo = LocalDate.parse(dataEmprestimo.getText()); // Lendo a data (no formato yyyy-MM-dd)
                        LocalDate localDateDevolucao = LocalDate.parse(dataDevolucao.getText());
                        Date emprestimosData = java.sql.Date.valueOf(localDateEmprestimo); // Convertendo para java.sql.Date
                        Date devolucaoData = java.sql.Date.valueOf(localDateDevolucao); // Convertendo para java.sql.Date

                        if (idLivro.getText().isEmpty() || idUsuario.getText().isEmpty() || idUsuario.getText().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                            return;
                        }

                        e1.setId_livro(Integer.parseInt(idLivro.getText()));
                        e1.setId_usuario(Integer.parseInt(idUsuario.getText()));
                        e1.setData_emprestimo(emprestimosData);
                        e1.setData_devolucao(devolucaoData);
                        e1.setStatus(status.getText());

                        try {
                            emDAO.adicionarEmprestimo(e1);
                            JOptionPane.showMessageDialog(null, "Empréstimo cadastrado com sucesso!");
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case Atualizar:
                        LocalDate localDateEmprestimo2 = LocalDate.parse(dataEmprestimo.getText()); // Lendo a data (no formato yyyy-MM-dd)
                        LocalDate localDateDevolucao2 = LocalDate.parse(dataDevolucao.getText());
                        Date emprestimosData2 = java.sql.Date.valueOf(localDateEmprestimo2); // Convertendo para java.sql.Date
                        Date devolucaoData2 = java.sql.Date.valueOf(localDateDevolucao2); // Convertendo para java.sql.Date

                        if (id.getText().isEmpty() || idLivro.getText().isEmpty() || idUsuario.getText().isEmpty() || dataEmprestimo.getText().isEmpty() || dataDevolucao.getText().isEmpty() || status.getText().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                            return;
                        }

                        e1.setId_emprestimos(Integer.parseInt(id.getText()));
                        e1.setId_livro(Integer.parseInt(idLivro.getText()));
                        e1.setId_usuario(Integer.parseInt(idUsuario.getText()));
                        e1.setData_emprestimo(emprestimosData2);
                        e1.setData_devolucao(devolucaoData2);
                        e1.setStatus(status.getText());

                        try {
                            emDAO.atualizarEmprestimo(e1);
                            JOptionPane.showMessageDialog(null, "Empréstimo atualizado com sucesso!");
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case Deletar:
                        if (id.getText().isEmpty()){
                            JOptionPane.showMessageDialog(null, "Preencha o campo do ID!");
                            return;
                        }
                        e1.setId_emprestimos(Integer.parseInt(id.getText()));

                        try {
                            emDAO.removerEmprestimo(e1);
                            JOptionPane.showMessageDialog(null, "Empréstimo removido com sucesso!");
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                }
            }
        });

        listarEmprestimosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    listarEmprestimos();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        limparCamposButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                id.setText("");
                idLivro.setText("");
                idUsuario.setText("");
                dataEmprestimo.setText("");
                dataDevolucao.setText("");
                status.setText("");
            }
        });

        registrarEmprestimo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popUpRegistrarEmprestimo();
            }
        });

        registrarDevolucao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popUpRegistrarDevolucao();
            }
        });
    }

    public void listarEmprestimos() throws SQLException {
        String[] colunas = {"id_emprestimo", "id_livro", "id_usuario", "data_emprestimo", "data_devolucao", "status"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        emprestimosTable.setModel(model);

        List<Emprestimos> emprestimos = emDAO.getListarEmprestimos();

        for (Emprestimos emprestimo : emprestimos) {
            model.addRow(new Object[] {
                    emprestimo.getId_emprestimos(),
                    emprestimo.getId_livro(),
                    emprestimo.getId_usuario(),
                    emprestimo.getData_emprestimo(),
                    emprestimo.getData_devolucao(),
                    emprestimo.getStatus()
            });
        }
    }

    public void popUpRegistrarEmprestimo() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Registrar Empréstimo");
        dialog.setSize(400, 200);
        dialog.setModal(true);
        dialog.setLocationRelativeTo(null);

        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints(); // Cria um objeto GridBagConstraints que guarda as regras de posicionamento de cada componente dentro do GridBagLayout. A cada add() de componente, usamos esse gbc para informar em qual célula, qual alinhamento, preenchimento e outros parâmetros.

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; // coluna 0
        gbc.gridy = 0; // linha 0
        dialog.add(new JLabel("Id do Livro:"), gbc);

        gbc.gridx = 1; // coluna 1
        gbc.gridy = 0; // linha 0
        JTextField idLivroField = new JTextField();
        idLivroField.setPreferredSize(new Dimension(200, 30));
        dialog.add(idLivroField, gbc);

        gbc.gridx = 0; // coluna 0
        gbc.gridy = 1; // linha 1
        dialog.add(new JLabel("Id do Usuário:"), gbc);

        gbc.gridx = 1; // coluna 1
        gbc.gridy = 1; // linha 1
        JTextField idUsuarioField = new JTextField();
        idUsuarioField.setPreferredSize(new Dimension(200, 30));
        dialog.add(idUsuarioField, gbc);

        gbc.gridx = 0; // coluna 0
        gbc.gridy = 2; // está na terceira linha (linha 2)
        gbc.gridwidth = 2; // o botão ocupa duas colunas,ou seja,se estende da coluna 0 até a 1 (ele fica centralizado horizontalmente).
        gbc.anchor = GridBagConstraints.CENTER; // alinha o botão no centro da área que ele ocupa.
        JButton salvarBtn = new JButton("Registrar Empréstimo");
        salvarBtn.setPreferredSize(new Dimension(200, 40));
        dialog.add(salvarBtn, gbc);

        salvarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idLivro = Integer.parseInt(idLivroField.getText());
                int idUsuario = Integer.parseInt(idUsuarioField.getText());

                try {
                    int qtd = emDAO.buscarQuantidadeLivros(idLivro);

                    LocalDate atual = LocalDate.now();
                    LocalDate devolucao = atual.plusDays(7);

                    java.sql.Date dataEmprestimo = java.sql.Date.valueOf(atual);
                    java.sql.Date dataDevolucao = java.sql.Date.valueOf(devolucao);


                    if (qtd > 0) {
                        Emprestimos emprestimo = new Emprestimos();
                        emprestimo.setId_livro(idLivro);
                        emprestimo.setId_usuario(idUsuario);
                        emprestimo.setData_emprestimo(dataEmprestimo);
                        emprestimo.setData_devolucao(dataDevolucao);
                        emprestimo.setStatus("Pendente");

                        emDAO.adicionarEmprestimo(emprestimo);
                        emDAO.reduzirQuantidadeLivros(idLivro);

                        JOptionPane.showMessageDialog(null, "Empréstimo registrado com sucesso!");
                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Livro indisponível no momento.");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(dialog, "Erro ao registrar empréstimo" + ex.getMessage());
                    throw new RuntimeException(ex);
                }
            }
        });
        dialog.setVisible(true);
    }

    public void popUpRegistrarDevolucao() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Registrar Empréstimo");
        dialog.setSize(400, 200);
        dialog.setModal(true);
        dialog.setLocationRelativeTo(null);

        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(new JLabel("ID do Empréstimo:"));

        gbc.gridx = 1;
        gbc.gridy = 0;
        JTextField idEmprestimoField = new JTextField();
        idEmprestimoField.setPreferredSize(new Dimension(200, 30));
        dialog.add(idEmprestimoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton salvarDevolucao = new JButton("Registrar Devolução");
        salvarDevolucao.setPreferredSize(new Dimension(200, 40));
        dialog.add(salvarDevolucao, gbc);


        salvarDevolucao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idEmprestimo = Integer.parseInt(idEmprestimoField.getText());

                try {
                    emDAO.registrarDevolucao(idEmprestimo);

                    JOptionPane.showMessageDialog(dialog, "Devolução registrada com sucesso!");
                    dialog.dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(dialog, "Erro ao registrar empréstimo" + ex.getMessage());
                    throw new RuntimeException(ex);
                }
            }
        });
        dialog.setVisible(true);
    }

}
