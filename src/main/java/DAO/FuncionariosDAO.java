package DAO;

import DB.Db;
import Logica.Funcionarios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FuncionariosDAO {

    private Connection conexao;

    public FuncionariosDAO() throws SQLException {
        this.conexao = Db.getConexao();
    }

    public List<Funcionarios> getListarFuncionarios() throws SQLException {
        String sql = "SELECT * FROM funcionarios;";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        ResultSet res = stmt.executeQuery();
        List<Funcionarios> listaFuncionarios = new ArrayList<Funcionarios>();

        while (res.next()) {
            Funcionarios f1 = new Funcionarios();

            f1.setId_funcionario(res.getInt("id_funcionario"));
            f1.setNome(res.getString("nome"));
            f1.setCargo(res.getString("cargo"));
            f1.setLogin(res.getString("login"));
            f1.setSenha(res.getString("senha"));

            listaFuncionarios.add(f1);
        }

        stmt.close();
        res.close();
        return listaFuncionarios;
    }

    public List<Funcionarios> getListarFuncionariosId(Funcionarios f1) throws SQLException {
        String sql = "SELECT * FROM funcionarios WHERE id_funcionario=?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setInt(1, f1.getId_funcionario());

        ResultSet res = stmt.executeQuery();
        List<Funcionarios> listaFuncionarios = new ArrayList<Funcionarios>();

        while (res.next()) {
            f1.setNome(res.getString("nome"));
            f1.setCargo(res.getString("cargo"));
            f1.setLogin(res.getString("login"));
            f1.setSenha(res.getString("senha"));
            listaFuncionarios.add(f1);
        }

        stmt.close();
        res.close();
        return listaFuncionarios;
    }

    public void adiconarFuncionario(Funcionarios f1) throws SQLException {
        String sql = "INSERT INTO funcionarios (nome, cargo, login, senha) VALUES (?, ?, ?, ?);";
        PreparedStatement stmt = conexao.prepareStatement(sql);

        stmt.setString(1, f1.getNome());
        stmt.setString(2, f1.getCargo());
        stmt.setString(3, f1.getLogin());
        stmt.setString(4, f1.getSenha());

        stmt.execute();
        stmt.close();
    }

    public void alterarFuncionario(Funcionarios f1) throws SQLException {
        String sql = "UPDATE funcionarios SET nome=?, cargo=?, login=?, senha=? WHERE id_funcionario=?;";
        PreparedStatement stmt = conexao.prepareStatement(sql);

        stmt.setString(1, f1.getNome());
        stmt.setString(2, f1.getCargo());
        stmt.setString(3, f1.getLogin());
        stmt.setString(4, f1.getSenha());
        stmt.setInt(5, f1.getId_funcionario());

        stmt.execute();
        stmt.close();
    }

    public void removerFuncionario(Funcionarios f1) throws SQLException {
        String sql = "DELETE FROM funcionarios WHERE id_funcionarios=?;";
        PreparedStatement stmt = conexao.prepareStatement(sql);

        stmt.setInt(1, f1.getId_funcionario());

        stmt.execute();
        stmt.close();
    }
}
