package br.com.zenon;

import java.nio.file.Path;
import java.util.List;

public class CotubaService {
    
    public void executar(ParametrosCotuba parametros) {
        var redenrizadorMarkDown = new RedenrizadorMarkDown();
        List<Capitulo> capitulos = redenrizadorMarkDown.redenrizar(parametros.getDiretorioDosMD());

        var ebook = new Ebook();
        var leitorPropriedadesEbook = new LeitorPropriedadesEbook();
        leitorPropriedadesEbook.ler(parametros.getDiretorioDosMD(), ebook);
        ebook.setCapitulos(capitulos);
        ebook.setArquivoSaida(parametros.getArquivoDeSaida());
        ebook.setFormato(parametros.getFormato());
        ebook.setArquivoSaida(parametros.getArquivoDeSaida());

        if (FormatoEbook.PDF.equals(ebook.getFormato())) {

            var geradorPDF = new GeradorPDF();
            geradorPDF.gerarPDF(ebook);

        } else if (FormatoEbook.EPUB.equals(ebook.getFormato())) {
            var geradorEPUB = new GeradorEPUB();
            geradorEPUB.gerarEPUB(ebook);

        }
    }
}
