package br.com.ucsal.olimpiadas;

import java.util.Scanner;

public class Menu {

    private Scanner in = new Scanner(System.in);

    public String exibirMenu() {
        System.out.println("\n=== OLIMPÍADA DE QUESTÕES (V1) ===");
            System.out.println("1) Cadastrar participante");
            System.out.println("2) Cadastrar prova");
            System.out.println("3) Cadastrar questão (A–E) em uma prova");
            System.out.println("4) Aplicar prova (selecionar participante + prova)");
            System.out.println("5) Listar tentativas (resumo)");
            System.out.println("0) Sair");
            System.out.print("> ");
        return in.nextLine();
    }

    public String lerTexto(String msg) {
        System.out.print(msg);
        return in.nextLine();
    }

}
