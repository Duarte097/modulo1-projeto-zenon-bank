package br.com.zenon;

import java.nio.file.Path;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@ApplicationScoped
public class CotubaService {

    private final RedenrizadorMarkDownCommonmark redenrizadorMarkDown;
    private final LeitorPropriedadesEbookArquivo leitorPropriedadesEbook;
    private final RepositorioMarkDownsDiretorio repositorioMarkDowns;
    private final GeradorEbook geradorPDF;
     private final GeradorEbook geradorEPUB;

    @Inject
    public CotubaService(RedenrizadorMarkDownCommonmark redenrizadorMarkDown, LeitorPropriedadesEbookArquivo leitorPropriedadesEbook, RepositorioMarkDownsDiretorio repositorioMarkDowns,@Named("geradorPDF") GeradorEbook geradorPDF, @Named("geradorEPUB") GeradorEbook geradorEPUB) {
        this.redenrizadorMarkDown = redenrizadorMarkDown;
        this.leitorPropriedadesEbook = leitorPropriedadesEbook;
        this.repositorioMarkDowns = repositorioMarkDowns;
        this.geradorPDF = geradorPDF;
        this.geradorEPUB = geradorEPUB;
    }
    
    public void executar(ParametrosCotuba parametros) {
        Path diretorioMDs = parametros.getDiretorioDosMD();

        List<Capitulo> capitulos = repositorioMarkDowns.buscar(parametros.getDiretorioDosMD());

        redenrizadorMarkDown.renderizar(capitulos);

        var ebook = new Ebook();
        leitorPropriedadesEbook.ler(parametros.getDiretorioDosMD(), ebook);
        ebook.setCapitulos(capitulos);
        ebook.setArquivoSaida(parametros.getArquivoDeSaida());
        ebook.setFormato(parametros.getFormato());
        ebook.setArquivoSaida(parametros.getArquivoDeSaida());

        GeradorEbook gerador;
        if (FormatoEbook.PDF.equals(ebook.getFormato())) {
            gerador = geradorPDF;
        } else if (FormatoEbook.EPUB.equals(ebook.getFormato())) {
            gerador = geradorEPUB;
        } else {
            throw new IllegalArgumentException("Formato de ebook inválido: " + parametros.getFormato());
        }

        gerador.gerar(ebook);
    }
}
