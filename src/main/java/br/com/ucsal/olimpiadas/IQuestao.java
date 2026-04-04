package br.com.ucsal.olimpiadas;

public interface IQuestao {
    long getId();
    String getEnunciado();
    boolean isRespostaCorreta(char resposta);
}
