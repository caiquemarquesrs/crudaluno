package br.com.puc.dao;

import br.com.puc.config.ConnectionFactory;
import br.com.puc.model.Aluno;
import br.com.puc.model.Cursos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO implements IAlunoDAO {
    @Override
    public void create(Aluno aluno) {
        String sql = "INSERT INTO alunos (nome, idade, curso) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, aluno.getNome());
            stmt.setInt(2, aluno.getIdade());
            stmt.setString(3, aluno.getCurso().name());
            stmt.executeUpdate();
            System.out.println("Aluno cadastrado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar aluno: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<Aluno> read() {
        String sql = "SELECT * FROM alunos";
        List<Aluno> alunos = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                int idade = rs.getInt("idade");
                String cursoStr = rs.getString("curso");
                Cursos curso = Cursos.valueOf(cursoStr);
                Aluno aluno = new Aluno(id, nome, idade, curso);
                alunos.add(aluno);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar alunos: " + e.getMessage());
            e.printStackTrace();
        }
        return alunos;
    }

    public List<Aluno> findByCurso(String curso) {
        List<Aluno> alunosDoCurso = new ArrayList<>();
        for (Aluno aluno : findAll()) {
            if (aluno.getCurso() != null && aluno.getCurso().toString().equalsIgnoreCase(curso)) {
                alunosDoCurso.add(aluno);
            }
        }
        return alunosDoCurso;
    }

    @Override
    public void update(Aluno aluno) {
        String sql = "UPDATE alunos SET nome = ?, idade = ?, curso = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, aluno.getNome());
            stmt.setInt(2, aluno.getIdade());
            stmt.setString(3, aluno.getCurso().name());
            stmt.setInt(4, aluno.getId());
            stmt.executeUpdate();
            System.out.println("Aluno atualizado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar aluno: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM alunos WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao excluir aluno: " + e.getMessage());
            return false;
        }
    }


    @Override
    public Aluno findById(int id) {
        String sql = "SELECT * FROM alunos WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nome = rs.getString("nome");
                    int idade = rs.getInt("idade");
                    String cursoStr = rs.getString("curso");
                    Cursos curso = Cursos.valueOf(cursoStr);
                    return new Aluno(id, nome, idade, curso);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar aluno por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Aluno> findByNome(String nome) {
        String sql = "SELECT * FROM alunos WHERE nome LIKE ?";
        List<Aluno> alunos = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nomeAluno = rs.getString("nome");
                    int idade = rs.getInt("idade");
                    String cursoStr = rs.getString("curso");
                    Cursos curso = Cursos.valueOf(cursoStr);
                    Aluno aluno = new Aluno(id, nomeAluno, idade, curso);
                    alunos.add(aluno);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar alunos por nome: " + e.getMessage());
            e.printStackTrace();
        }
        return alunos;
    }

    public List<Aluno> findAll() {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM alunos";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setId(rs.getInt("id"));
                aluno.setNome(rs.getString("nome"));
                aluno.setIdade(rs.getInt("idade"));
                aluno.setCurso(Cursos.valueOf(rs.getString("curso")));
                alunos.add(aluno);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar todos os alunos: " + e.getMessage());
        }

        return alunos;
    }


    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS alunos (" + "id INTEGER PRIMARY KEY AUTOINCREMENT," + "nome TEXT NOT NULL," + "idade INTEGER NOT NULL," + "curso TEXT NOT NULL)";
        try (Connection conn = ConnectionFactory.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela alunos criada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela: " + e.getMessage());
            e.printStackTrace();
        }
    }
}