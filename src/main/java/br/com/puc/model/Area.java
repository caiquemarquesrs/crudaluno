package br.com.puc.model;

public enum Area {
    EXATAS("Exatas"),
    HUMANAS("Humanas"),
    BIOLOGICAS("Biológicas"),
    ARTES("Artes");

    private final String descricao;

    Area(String descricao) {
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
