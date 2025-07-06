import DB.Db;
import GUI.TelaLogin;
import GUI.TelaPrincipal.TelaPrincipal;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException {
        Connection con = Db.getConexao();
        con.close();
        System.err.println("Desconectado do banco");

        SwingUtilities.invokeLater(() -> {
            new TelaLogin().setVisible(true);
        });
    }
}
