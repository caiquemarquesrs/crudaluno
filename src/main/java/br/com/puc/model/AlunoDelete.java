package br.com.puc.model;

import br.com.puc.dao.AlunoDAO;
import br.com.puc.model.Aluno;

import java.util.List;

public class AlunoDelete {
    public static void main(String[] args) {
        AlunoDAO alunoDAO = new AlunoDAO();
        List<Aluno> alunos = alunoDAO.findByNome("João");
        if (!alunos.isEmpty()) {
            Aluno aluno = alunos.get(0);
            System.out.println("Aluno a ser excluído: " + aluno);
            boolean sucesso = alunoDAO.delete(aluno.getId());
            if (sucesso) {
                System.out.println("Aluno excluído com sucesso!");
                Aluno alunoExcluido = alunoDAO.findById(aluno.getId());
                if (alunoExcluido == null) {
                    System.out.println("Verificação: Aluno não encontrado no banco de dados.");
                } else {
                    System.out.println("Erro: Aluno ainda existe no banco de dados.");
                }
            } else {
                System.out.println("Erro ao excluir o aluno.");
            }
        } else {
            System.out.println("Nenhum aluno encontrado com o nome 'João'");
        }
    }
}
