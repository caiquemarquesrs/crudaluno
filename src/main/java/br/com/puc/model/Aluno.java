package br.com.puc.model;

import br.com.puc.model.Cursos;

public class Aluno {
    private int id;
    private String nome;
    private int idade;
    private Cursos curso;

    public Aluno() {
    }

    public Aluno(String nome, int idade, Cursos curso) {
        this.nome = nome;
        this.idade = idade;
        this.curso = curso;
    }

    public Aluno(int id, String nome, int idade, Cursos curso) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.curso = curso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public Cursos getCurso() {
        return curso;
    }

    public void setCurso(Cursos curso) {
        this.curso = curso;
    }

    @Override
    public String toString() {
        return "Aluno{" + "id=" + id + ", nome='" + nome + '\'' + ", idade=" + idade + ", curso=" + curso + '}';
    }
}