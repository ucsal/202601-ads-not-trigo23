package br.com.ucsal.olimpiadas;

import java.util.List;

public class TentativaService {
private final BancoMemoria banco;

    public TentativaService(BancoMemoria banco) {
        this.banco = banco;
    }
 

    public Tentativa iniciar(long participanteId, long provaId) {
        Tentativa t = new Tentativa();
        t.setId(banco.proximaTentativaId++);
        t.setParticipanteId(participanteId);
        t.setProvaId(provaId);
        return t;
    }

    public void salvar(Tentativa t) {
        banco.tentativas.add(t);
    }

    public List<Tentativa> listar() {
        return banco.tentativas;
    }
}
