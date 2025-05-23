package br.com.puc.model;

import br.com.puc.dao.AlunoDAO;
import br.com.puc.dao.CursoDAO;
import br.com.puc.model.Aluno;
import br.com.puc.model.Area;
import br.com.puc.model.Curso;

public class AlunoCreate {
    public static void main(String[] args) {
        AlunoDAO alunoDAO = new AlunoDAO();
        alunoDAO.createTable();
        CursoDAO cursoDAO = new CursoDAO();
        Curso curso = new Curso("Engenharia de Software", "ENGSW", Area.EXATAS);
        cursoDAO.create(curso);
        Aluno aluno = new Aluno("João Silva", 22, "ENGSW");
        alunoDAO.create(aluno);
        System.out.println("Aluno criado com sucesso!");
    }
}
