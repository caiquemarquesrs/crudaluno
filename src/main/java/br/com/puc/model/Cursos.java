package br.com.puc.model;

public enum Cursos {
    ENGENHARIA("Engenharia"),
    MEDICINA("Medicina"),
    DIREITO("Direito"),
    ADMINISTRACAO("Administração"),
    COMPUTACAO("Computação"),
    ARQUITETURA("Arquitetura"),
    PSICOLOGIA("Psicologia"),
    ENFERMAGEM("Enfermagem");

    private final String descricao;

    Cursos(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
