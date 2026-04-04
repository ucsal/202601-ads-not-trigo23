package br.com.ucsal.olimpiadas;

public class ParticipanteService {

    private final BancoMemoria banco;

    public ParticipanteService(BancoMemoria banco) {
        this.banco = banco;
    }

    public Participante cadastrar(String nome, String email) {

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome inválido");
        }

        Participante p = new Participante();
        p.setId(banco.proximoParticipanteId++);
        p.setNome(nome);
        p.setEmail(email);

        banco.participantes.add(p);

        return p;
    }
}


