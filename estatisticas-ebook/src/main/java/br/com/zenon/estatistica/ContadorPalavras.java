package br.com.zenon.estatistica;

import java.util.TreeMap;

public class ContadorPalavras extends TreeMap<String, Integer> {
    public void adicionarPalavra(String palavra){
        super.merge(palavra, 1, Integer::sum);
    }
}
