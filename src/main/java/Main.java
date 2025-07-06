import DAO.LivrosDAO;
import DAO.UsuariosDAO;
import DB.Db;

import GUI.TelaLogin;
import Logica.Livros;
import Logica.Usuarios;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection con = Db.getConexao();
        con.close();
        System.err.println("Desconectado do banco");

        SwingUtilities.invokeLater(() -> {
            new TelaLogin().setVisible(true);
        });
    }
}