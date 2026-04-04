package br.com.ucsal.olimpiadas;

import java.util.List;

public class QuestaoService {
private final BancoMemoria banco;

    public QuestaoService(BancoMemoria banco) {
        this.banco = banco;
    }

    public Questao cadastrar(long provaId, String enunciado, String[] alternativas, char correta) {
        
        Questao q = new Questao();
        q.setId(banco.proximaQuestaoId++);
        q.setProvaId(provaId);
        q.setEnunciado(enunciado);
        q.setAlternativas(alternativas);
        q.setAlternativaCorreta(correta);

        banco.questoes.add(q);
        return q;
    }

    public List<Questao> buscarPorProva(long provaId) {
        return banco.questoes.stream()
                .filter(q -> q.getProvaId() == provaId)
                .toList();
    }
}
