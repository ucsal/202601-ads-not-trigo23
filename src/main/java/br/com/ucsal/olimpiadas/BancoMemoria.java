package br.com.ucsal.olimpiadas;

import java.util.ArrayList;
import java.util.List;

public class BancoMemoria {

    public long proximoParticipanteId = 1;
    public long proximaProvaId = 1;
    public long proximaQuestaoId = 1;
    public long proximaTentativaId = 1;

    public final List<Participante> participantes = new ArrayList<>();
    public final List<Prova> provas = new ArrayList<>();
    public final List<Questao> questoes = new ArrayList<>();
    public final List<Tentativa> tentativas = new ArrayList<>();
}

