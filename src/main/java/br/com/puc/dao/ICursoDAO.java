package br.com.puc.dao;

import br.com.puc.model.Area;
import br.com.puc.model.Curso;

import java.util.List;

public interface ICursoDAO {
    void create(Curso curso);
    List<Curso> read();
    void update(Curso curso);
    boolean delete(Long codigo);
    Curso findById(Long codigo);
    List<Curso> findByArea(Area area);
    Curso findBySigla(String sigla);
    List<Curso> findAll();
}
