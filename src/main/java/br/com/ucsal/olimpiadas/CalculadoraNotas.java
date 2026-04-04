package br.com.ucsal.olimpiadas;

public class CalculadoraNotas implements ICalculadora {
     @Override
    public int calcular(Tentativa tentativa) {
        int acertos = 0;

        for (Resposta r : tentativa.getRespostas()) {
            if (r.isCorreta()) {
                acertos++;
            }
        }

        return acertos;
    }

}
