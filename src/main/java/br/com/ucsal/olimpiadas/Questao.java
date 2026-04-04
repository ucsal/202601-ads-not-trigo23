package br.com.ucsal.olimpiadas;


public class Questao implements IQuestao {

    private long id;
    private long provaId;
    private String enunciado;
    private String[] alternativas;
    private char alternativaCorreta;
	private String fenInicial;            

    public Questao() {
    }

    // ========================
    // GETTERS / SETTERS
    // ========================

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProvaId() {
        return provaId;
    }

    public void setProvaId(long provaId) {
        this.provaId = provaId;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        if (enunciado == null || enunciado.isBlank()) {
            throw new IllegalArgumentException("Enunciado inválido");
        }
        this.enunciado = enunciado;
    }

    public String[] getAlternativas() {
        return alternativas.clone(); // 🔒 protege o array
    }

    public void setAlternativas(String[] alternativas) {
        if (alternativas == null || alternativas.length != 5) {
            throw new IllegalArgumentException("Devem existir 5 alternativas (A–E)");
        }
        this.alternativas = alternativas.clone(); // 🔒 evita alteração externa
    }

    public char getAlternativaCorreta() {
        return alternativaCorreta;
    }

    public void setAlternativaCorreta(char alternativaCorreta) {
        this.alternativaCorreta = normalizar(alternativaCorreta);
    }

    // ========================
    // REGRAS DE NEGÓCIO
    // ========================

    @Override
    public boolean isRespostaCorreta(char resposta) {
        return normalizar(resposta) == alternativaCorreta;
    }

    // ========================
    // UTILITÁRIO (baixo acoplamento)
    // ========================

    public static char normalizar(char c) {
        c = Character.toUpperCase(c);

        if (c >= 'A' && c <= 'E') {
            return c;
        }

        throw new IllegalArgumentException("Alternativa inválida: " + c);
    }

	public String getFenInicial() {
    return fenInicial;
}

public void setFenInicial(String fenInicial) {
    this.fenInicial = fenInicial;
}
}