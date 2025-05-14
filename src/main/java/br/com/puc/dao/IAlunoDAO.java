package br.com.puc.dao;

import br.com.puc.model.Aluno;

import java.util.List;

public interface IAlunoDAO {
    void create(Aluno aluno);

    List<Aluno> read();

    void update(Aluno aluno);

    boolean delete(int id);

    Aluno findById(int id);

    List<Aluno> findByNome(String nome);
}