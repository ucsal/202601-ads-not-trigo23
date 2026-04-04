package br.com.ucsal.olimpiadas;

import java.util.Scanner;

public class App {

    static final Scanner in = new Scanner(System.in);

    static BancoMemoria banco = new BancoMemoria();

    static ParticipanteService participanteService = new ParticipanteService(banco);
    static ProvaService provaService = new ProvaService(banco);
    static QuestaoService questaoService = new QuestaoService(banco);
    static TentativaService tentativaService = new TentativaService(banco);
    static CalculadoraNotas calculadora = new CalculadoraNotas();

    public static void main(String[] args) {

        seed();

        while (true) {
            System.out.println("\n=== OLIMPÍADA DE QUESTÕES (V1) ===");
            System.out.println("1) Cadastrar participante");
            System.out.println("2) Cadastrar prova");
            System.out.println("3) Cadastrar questão (A–E) em uma prova");
            System.out.println("4) Aplicar prova (selecionar participante + prova)");
            System.out.println("5) Listar tentativas (resumo)");
            System.out.println("0) Sair");
            System.out.print("> ");

            switch (in.nextLine()) {
                case "1" -> cadastrarParticipante();
                case "2" -> cadastrarProva();
                case "3" -> cadastrarQuestao();
                case "4" -> aplicarProva();
                case "5" -> listarTentativas();
                case "0" -> {
                    System.out.println("tchau");
                    return;
                }
                default -> System.out.println("opção inválida");
            }
        }
    }

    static void cadastrarParticipante() {
        System.out.print("Nome: ");
        var nome = in.nextLine();

        System.out.print("Email (opcional): ");
        var email = in.nextLine();

        try {
            var p = participanteService.cadastrar(nome, email);
            System.out.println("Participante cadastrado: " + p.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void cadastrarProva() {
        System.out.print("Título da prova: ");
        var titulo = in.nextLine();

        try {
            var prova = provaService.cadastrar(titulo);
            System.out.println("Prova criada: " + prova.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void cadastrarQuestao() {
        if (banco.provas.isEmpty()) {
            System.out.println("não há provas cadastradas");
            return;
        }

        var provaId = escolherProva();
        if (provaId == null) return;

        System.out.println("Enunciado:");
        var enunciado = in.nextLine();

        var alternativas = new String[5];
        for (int i = 0; i < 5; i++) {
            char letra = (char) ('A' + i);
            System.out.print("Alternativa " + letra + ": ");
            alternativas[i] = letra + ") " + in.nextLine();
        }

        System.out.print("Alternativa correta (A–E): ");
        char correta;
        try {
            correta = Questao.normalizar(in.nextLine().trim().charAt(0));
        } catch (Exception e) {
            System.out.println("alternativa inválida");
            return;
        }

        questaoService.cadastrar(provaId, enunciado, alternativas, correta);
        System.out.println("Questão cadastrada!");
    }

    static void aplicarProva() {

        if (banco.participantes.isEmpty()) {
            System.out.println("cadastre participantes primeiro");
            return;
        }

        if (banco.provas.isEmpty()) {
            System.out.println("cadastre provas primeiro");
            return;
        }

        var participanteId = escolherParticipante();
        if (participanteId == null) {
            System.out.println("participante inválido");
            return;
        }

        var provaId = escolherProva();
        if (provaId == null) {
            System.out.println("prova inválida");
            return;
        }

        var questoes = questaoService.buscarPorProva(provaId);

        if (questoes.isEmpty()) {
            System.out.println("esta prova não possui questões cadastradas");
            return;
        }

        var tentativa = tentativaService.iniciar(participanteId, provaId);

        System.out.println("\n--- Início da Prova ---");

        for (var q : questoes) {

            System.out.println("\nQuestão #" + q.getId());
            System.out.println(q.getEnunciado());

            if (q.getFenInicial() != null) {
                System.out.println("Posição inicial:");
                imprimirTabuleiroFen(q.getFenInicial());
            }

            for (var alt : q.getAlternativas()) {
                System.out.println(alt);
            }

            System.out.print("Sua resposta (A–E): ");

            char marcada;
            try {
                marcada = Questao.normalizar(in.nextLine().trim().charAt(0));
            } catch (Exception e) {
                System.out.println("resposta inválida (marcando como errada)");
                marcada = 'X';
            }

            var r = new Resposta();
            r.setQuestaoId(q.getId());
            r.setAlternativaMarcada(marcada);
            r.setCorreta(q.isRespostaCorreta(marcada));

            tentativa.getRespostas().add(r);
        }

        tentativaService.salvar(tentativa);

        int nota = calculadora.calcular(tentativa);

        System.out.println("\n--- Fim da Prova ---");
        System.out.println("Nota (acertos): " + nota + " / " + tentativa.getRespostas().size());
    }

    static void listarTentativas() {
        System.out.println("\n--- Tentativas ---");

        for (var t : tentativaService.listar()) {
            System.out.printf("#%d | participante=%d | prova=%d | nota=%d/%d%n",
                    t.getId(),
                    t.getParticipanteId(),
                    t.getProvaId(),
                    calculadora.calcular(t),
                    t.getRespostas().size());
        }
    }

   static Long escolherParticipante() {
    System.out.println("\nParticipantes:");
    for (var p : banco.participantes) {
        System.out.printf(" %d) %s%n", p.getId(), p.getNome());
    }

    System.out.print("Escolha o id do participante: ");

    try {
        long id = Long.parseLong(in.nextLine());

        boolean existe = banco.participantes.stream()
                .anyMatch(p -> p.getId() == id);

        if (!existe) {
            System.out.println("id inválido");
            return null;
        }

        return id;

    } catch (Exception e) {
        System.out.println("entrada inválida");
        return null;
    }
}
   static Long escolherProva() {
    System.out.println("\nProvas:");
    for (var p : banco.provas) {
        System.out.printf(" %d) %s%n", p.getId(), p.getTitulo());
    }

    System.out.print("Escolha o id da prova: ");

    try {
        long id = Long.parseLong(in.nextLine());

        boolean existe = banco.provas.stream()
                .anyMatch(p -> p.getId() == id);

        if (!existe) {
            System.out.println("id inválido");
            return null;
        }

        return id;

    } catch (Exception e) {
        System.out.println("entrada inválida");
        return null;
    }
}

    static void imprimirTabuleiroFen(String fen) {
        String[] ranks = fen.split(" ")[0].split("/");

        System.out.println("\n a b c d e f g h");
        System.out.println(" -----------------");

        for (int r = 0; r < 8; r++) {
            System.out.print((8 - r) + " | ");
            for (char c : ranks[r].toCharArray()) {
                if (Character.isDigit(c)) {
                    for (int i = 0; i < c - '0'; i++) System.out.print(". ");
                } else {
                    System.out.print(c + " ");
                }
            }
            System.out.println("| " + (8 - r));
        }

        System.out.println(" -----------------");
        System.out.println(" a b c d e f g h\n");
    }

    static void seed() {
    var prova = provaService.cadastrar("Olimpíada 2026 • Nível 1 • Prova A");

    // QUESTÃO 1
    var q1 = questaoService.cadastrar(
            prova.getId(),
            "Questão 1 — Mate em 1.\nÉ a vez das brancas.\nEncontre o lance que dá mate imediatamente.",
            new String[]{"A) Qh7#", "B) Qf5#", "C) Qc8#", "D) Qh8#", "E) Qe6#"},
            'C'
    );
    q1.setFenInicial("6k1/5ppp/8/8/8/7Q/6PP/6K1 w - - 0 1");

    // QUESTÃO 2
    var q2 = questaoService.cadastrar(
            prova.getId(),
            "Questão 2 — Mate em 1.\nQual lance finaliza a partida?",
            new String[]{"A) Qb8#", "B) Qxg7#", "C) Qe5#", "D) Qf4#", "E) Qh4#"},
            'B'
    );
    q2.setFenInicial("7k/6pp/8/8/8/6Q1/6PP/6K1 w - - 0 1");

    // QUESTÃO 3
    var q3 = questaoService.cadastrar(
            prova.getId(),
            "Questão 3 — Mate em 1.\nEncontre o mate imediato.",
            new String[]{"A) Qa8#", "B) Qxf7#", "C) Qd5#", "D) Qb3#", "E) Qe2#"},
            'B'
    );
    q3.setFenInicial("6k1/5ppp/8/8/8/5Q2/6PP/6K1 w - - 0 1");

    // QUESTÃO 4
    var q4 = questaoService.cadastrar(
            prova.getId(),
            "Questão 4 — Mate em 1.\nQual é o lance vencedor?",
            new String[]{"A) Qa2#", "B) Qxf7#", "C) Qc4#", "D) Qe1#", "E) Qb6#"},
            'B'
    );
    q4.setFenInicial("6k1/5ppp/8/8/8/8/5QPP/6K1 w - - 0 1");

    // QUESTÃO 5
    var q5 = questaoService.cadastrar(
            prova.getId(),
            "Questão 5 — Mate em 1.\nEncontre o cheque-mate.",
            new String[]{"A) Qf8#", "B) Qxg7#", "C) Qh5#", "D) Qe6#", "E) Qf3#"},
            'A'
    );
    q5.setFenInicial("7k/5Qpp/8/8/8/8/6PP/6K1 w - - 0 1");
}
}