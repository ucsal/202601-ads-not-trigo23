package br.com.ucsal.olimpiadas;

public class CorretorEscolhas implements ICorretorQuestao {
 @Override
    public boolean corrigir(Questao questao, char resposta) {
        return normalizar(resposta) == questao.getAlternativaCorreta();
    }

    private char normalizar(char c) {
        char up = Character.toUpperCase(c);
        if (up < 'A' || up > 'E') {
            throw new IllegalArgumentException("Alternativa deve estar entre A e E.");
        }
        return up;
    }
}
