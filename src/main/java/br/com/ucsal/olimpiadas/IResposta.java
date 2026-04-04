package br.com.ucsal.olimpiadas;

public interface IResposta {
        long getQuestaoId();
        char getAlternativaMarcada();
        boolean isCorreta();

}
