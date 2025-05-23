package br.com.puc.dao;

import br.com.puc.config.ConnectionFactory;
import br.com.puc.model.Area;
import br.com.puc.model.Curso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO implements ICursoDAO {

    @Override
    public void create(Curso curso) {
        String sql = "INSERT INTO cursos (nome, sigla, area) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, curso.getNome());
            stmt.setString(2, curso.getSigla());
            stmt.setString(3, curso.getArea().name());
            stmt.executeUpdate();
            System.out.println("Curso cadastrado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar curso: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<Curso> read() {
        return findAll();
    }

    @Override
    public void update(Curso curso) {
        String sql = "UPDATE cursos SET nome = ?, sigla = ?, area = ? WHERE codigo = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, curso.getNome());
            stmt.setString(2, curso.getSigla());
            stmt.setString(3, curso.getArea().name());
            stmt.setLong(4, curso.getCodigo());
            stmt.executeUpdate();
            System.out.println("Curso atualizado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar curso: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public boolean delete(Long codigo) {
        String sql = "DELETE FROM cursos WHERE codigo = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, codigo);
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir curso: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Curso findById(Long codigo) {
        String sql = "SELECT * FROM cursos WHERE codigo = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, codigo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractCursoFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar curso por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Curso> findByArea(Area area) {
        String sql = "SELECT * FROM cursos WHERE area = ?";
        List<Curso> cursos = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, area.name());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    cursos.add(extractCursoFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar cursos por área: " + e.getMessage());
            e.printStackTrace();
        }
        return cursos;
    }

    @Override
    public Curso findBySigla(String sigla) {
        String sql = "SELECT * FROM cursos WHERE sigla = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sigla);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractCursoFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar curso por sigla: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Curso> findAll() {
        String sql = "SELECT * FROM cursos";
        List<Curso> cursos = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                cursos.add(extractCursoFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar cursos: " + e.getMessage());
            e.printStackTrace();
        }
        return cursos;
    }

    private Curso extractCursoFromResultSet(ResultSet rs) throws SQLException {
        Long codigo = rs.getLong("codigo");
        String nome = rs.getString("nome");
        String sigla = rs.getString("sigla");
        Area area = Area.valueOf(rs.getString("area"));
        return new Curso(codigo, nome, sigla, area);
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS cursos (" +
                "codigo INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "sigla TEXT NOT NULL UNIQUE," +
                "area TEXT NOT NULL)";
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela cursos criada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela cursos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
