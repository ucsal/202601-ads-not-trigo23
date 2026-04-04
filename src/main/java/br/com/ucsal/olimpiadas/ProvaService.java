package br.com.ucsal.olimpiadas;

import java.util.List;

public class ProvaService {
private final BancoMemoria banco;

    public ProvaService(BancoMemoria banco) {
        this.banco = banco;
    }

    public Prova cadastrar(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("Título inválido");
        }

        Prova prova = new Prova();
        prova.setId(banco.proximaProvaId++);
        prova.setTitulo(titulo);

        banco.provas.add(prova);
        return prova;
    }

    public List<Prova> listar() {
        return banco.provas;
    }
}
