package br.com.puc.model;

public class Curso {
    private Long codigo;
    private String nome;
    private String sigla;
    private Area area;

    public Curso() {
    }

    public Curso(String nome, String sigla, Area area) {
        this.nome = nome;
        this.sigla = sigla;
        this.area = area;
    }

    public Curso(Long codigo, String nome, String sigla, Area area) {
        this.codigo = codigo;
        this.nome = nome;
        this.sigla = sigla;
        this.area = area;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "Curso{" +
                "codigo=" + codigo +
                ", nome='" + nome + '\'' +
                ", sigla='" + sigla + '\'' +
                ", area=" + area +
                '}';
    }
}
