package br.com.puc.model;

public class Aluno {
    private int id;
    private String nome;
    private int idade;
    private String cursoSigla;
    private Curso curso;

    public Aluno() {
    }

    public Aluno(String nome, int idade, String cursoSigla) {
        this.nome = nome;
        this.idade = idade;
        this.cursoSigla = cursoSigla;
    }

    public Aluno(int id, String nome, int idade, String cursoSigla) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.cursoSigla = cursoSigla;
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

    public String getCursoSigla() {
        return cursoSigla;
    }

    public void setCursoSigla(String cursoSigla) {
        this.cursoSigla = cursoSigla;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
        if (curso != null) {
            this.cursoSigla = curso.getSigla();
        }
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", idade=" + idade +
                ", cursoSigla='" + cursoSigla + '\'' +
                '}';
    }
}
