package br.com.zenon;

import java.nio.file.Path;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Any;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@ApplicationScoped
public class CotubaService {

    private final RedenrizadorMarkDownCommonmark redenrizadorMarkDown;
    private final LeitorPropriedadesEbookArquivo leitorPropriedadesEbook;
    private final RepositorioMarkDownsDiretorio repositorioMarkDowns;
    private final InstanceManager<GeradorEbook> geradorPDFInstanceManager;
    private final InstanceManager<GeradorEbook> geradorEPUBInstanceManager;

    @Inject
    public CotubaService(RedenrizadorMarkDownCommonmark redenrizadorMarkDown, LeitorPropriedadesEbookArquivo leitorPropriedadesEbook, RepositorioMarkDownsDiretorio repositorioMarkDowns, @Any InstanceManager<GeradorEbook> geradorPDFInstanceManager, @Any InstanceManager<GeradorEbook> geradorEPUBInstanceManager) {
        this.redenrizadorMarkDown = redenrizadorMarkDown;
        this.leitorPropriedadesEbook = leitorPropriedadesEbook;
        this.repositorioMarkDowns = repositorioMarkDowns;
        this.geradorPDFInstanceManager = geradorPDFInstanceManager;
        this.geradorEPUBInstanceManager = geradorEPUBInstanceManager;
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
        FormatoEbook formato = ebook.getFormato();
        GeradorEbook geradorEbook = gerador.select(FormatoEbookFilter.of(formato)).get();

        gerador.gerar(ebook);
    }
}
