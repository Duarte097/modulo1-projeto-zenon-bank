package br.com.zenon.estatistica;

import java.util.TreeMap;

public class ContadorPalavras implements Iterable<ContadorPalavras.ContagemPalavra> {

    @Override
    public Iterator<ContagemPalavra> iterator(){
        Iterator<Map.Entry<String, Integer>> entryIterator = mapa.entrySet().iterator();
        return new Iterator<>(){
            @Override
            public boolean hasNext() {
                return entryIterator.hasNext();
            }

            @Override
            public ContagemPalavra next() {
                Map.Entry<String, Integer> entry = entryIterator.next();
                return new ContagemPalavra(entry.getKey(), entry.getValue());
            }
        };
    }

    record ContagemPalavra(String palavra, int ocorrencias) {}

    private Map<String, Integer> mapa = new TreeMap<>();

    public void adicionarPalavra(String palavra){
        mapa.merge(palavra, 1, Integer::sum);
    }

    public Iterable<? extends Map.Entry<String, Integer>> entrySet(){
        return mapa.entrySet();
    }
}
