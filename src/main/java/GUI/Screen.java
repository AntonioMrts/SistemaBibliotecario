package GUI;

import javax.swing.*;
import java.sql.SQLException;

public class Screen extends JFrame {
//    public Screen() {
//        // Moldura da tela
//        setTitle("Trabalho de POO");
//        setVisible(true); // deixar a tela visível
//        setSize(800, 500); // layout da tela
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // fecha de fato a janela
//        setResizable(false); // bloqueia aumentar/diminuir tamanho
//        setLocationRelativeTo(null); // a janela fica centralizada na tela
//
//    }

    public static void main(String[] args) throws SQLException {
        BooksEditor b = new BooksEditor();
        b.setResizable(false);
        b.setVisible(true);
    }
}
