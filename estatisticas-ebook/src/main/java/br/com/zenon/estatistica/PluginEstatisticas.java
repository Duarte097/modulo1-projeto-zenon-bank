package br.com.zenon.estatistica;


import org.jsoup.Jsoup;
import org.w3c.dom.Document;

import java.util.Map;

public class PluginEstatisticas implements CotubaPlugin {

    @Override
    public String aposRenderizacao(String html){
        return "";
    }

    @Override
    public void aposGeracao(Ebook ebook){

        ContadorPalavras contador = new ContadorPalavras();

        for(Capitulo capitulo : ebook.capitulos()){
            String html = capitulo.html();
            Document doc = Jsoup.parseBodyFragment(html);

            String textoCapitulo = doc.text().toLowerCase();
            textoCapitulo.replaceAll("\\p{Punct}", "");

            String[] palavras = textoCapitulo.split("\\s+");
            for(String palavra : palavras){
                contador.adicionarPalavra(palavra);
            }
        }

        for(ContadorPalavras.ContagemPalavra contagem : contador){
            String palavra = contagem.palavra();
            Integer ocorrencias = contagem.ocorrencias();
            System.out.printf("'%s' : %d\n", contagem.palavra(), contagem.ocorrencias());
        }
    }
}
