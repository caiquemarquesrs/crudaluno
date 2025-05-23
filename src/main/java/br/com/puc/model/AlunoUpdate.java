package br.com.puc.test;

import br.com.puc.dao.AlunoDAO;
import br.com.puc.model.Aluno;

import java.util.List;

public class AlunoUpdate {
    public static void main(String[] args) {
        AlunoDAO alunoDAO = new AlunoDAO();
        List<Aluno> alunos = alunoDAO.findByNome("João");
        if (!alunos.isEmpty()) {
            Aluno aluno = alunos.get(0);
            System.out.println("Aluno antes da atualização: " + aluno);
            aluno.setNome("João Oliveira Silva");
            aluno.setIdade(23);
            alunoDAO.update(aluno);
            Aluno alunoAtualizado = alunoDAO.findById(aluno.getId());
            System.out.println("Aluno após a atualização: " + alunoAtualizado);
        } else {
            System.out.println("Nenhum aluno encontrado com o nome 'João'");
        }
    }
}
