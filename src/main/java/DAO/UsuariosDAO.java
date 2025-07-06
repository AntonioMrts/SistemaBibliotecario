package DAO;

import DB.Db;
import Logica.Usuarios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuariosDAO {
    private Connection conexao;

    public UsuariosDAO() throws SQLException {
        this.conexao = Db.getConexao();
    }

    public List<Usuarios> getListarUsuarios() throws SQLException {
        String sql = "SELECT * FROM usuarios;";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        ResultSet res = stmt.executeQuery();
        List<Usuarios> listaUsuarios = new ArrayList<Usuarios>();
        while (res.next()) {
            Usuarios u1 = new Usuarios();
            u1.setId_usuario(res.getInt("ID_Usuario"));
            u1.setNome(res.getString("Nome"));
            u1.setEmail(res.getString("Email"));
            u1.setCpf(res.getString("Cpf"));
            u1.setTelefone(res.getString("Telefone"));

            listaUsuarios.add(u1);
        }

        stmt.close();
        res.close();
        return listaUsuarios;
    }

    public List<Usuarios> getListarUsuariosId(Usuarios u1) throws  SQLException {
        String sql = "SELECT * FROM usuarios WHERE ID_Usuarios=?;";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setInt(1, u1.getId_usuario());

        ResultSet res = stmt.executeQuery();
        List<Usuarios> listarUsuariosId = new ArrayList<Usuarios>();

        while (res.next()) {
            u1.setId_usuario(res.getInt("ID_Usuario"));
            u1.setNome(res.getString("Nome"));
            u1.setEmail(res.getString("Email"));
            u1.setCpf(res.getString("Cpf"));
            u1.setTelefone(res.getString("Telefone"));
            listarUsuariosId.add(u1);
        }

        res.close();
        stmt.close();
        return listarUsuariosId;
    }

    public void adicionarUsuario(Usuarios u1) throws SQLException {
        String sql = "INSERT INTO usuarios (Nome, Email, Cpf, Telefone) VALUES (?, ?, ?, ?);";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setString(1, u1.getNome());
        stmt.setString(2, u1.getEmail());
        stmt.setString(3, u1.getCpf());
        stmt.setString(4, u1.getTelefone());

        stmt.execute();
        stmt.close();
    }

    public void alterarUsuario(Usuarios u1) throws SQLException {
        String sql = "UPDATE usuarios SET Nome=?, Email=?, Cpf=?, Telefone=? WHERE ID_Usuario=?;";
        PreparedStatement stmt = conexao.prepareStatement(sql);

        stmt.setString(1, u1.getNome());
        stmt.setString(2, u1.getEmail());
        stmt.setString(3, u1.getCpf());
        stmt.setString(4, u1.getTelefone());
        stmt.setInt(5, u1.getId_usuario());

        stmt.execute();
        stmt.close();
    }

    public void removerUsuario(Usuarios u1) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE ID_Usuario=?;";
        PreparedStatement stmt = conexao.prepareStatement(sql);

        stmt.setInt(1, u1.getId_usuario());

        stmt.execute();
        stmt.close();
    }
}
