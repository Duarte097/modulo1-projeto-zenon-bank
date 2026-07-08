package br.com.zenon;

import java.nio.file.Path;
import java.util.List;

import org.jmolecules.ddd.annotation.Service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Any;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Service    
@ApplicationScoped
public class CotubaService implements CotubaUseCase {

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
        Path diretorioMDs = parametros.diretorioDosMD();

        List<MarkDown> markDowns = repositorioMarkDowns.buscar(parametros.diretorioDosMD());

        List<Capitulo> capitulos = redenrizadorMarkDown.renderizar(markDowns);

        var propriedadesEbook = leitorPropriedadesEbook.ler(diretorioMDs);
        var ebook = EbookBuilder.builder()
            .capitulos(capitulos)
            .arquivoSaida(parametros.arquivoDeSaida())
            .formato(parametros.formato())
            .arquivoSaida(parametros.arquivoDeSaida())
            .titulo(propriedadesEbook.titulo())
            .autor(propriedadesEbook.autor())
            .build();

        GeradorEbook geradorEbook = geradoresEbook.select(FormatoEbookFilter.of(ebook.formato())).get();

        geradorEbook.gerar(ebook, parametros.arquivoDeSaida());

        for (CotubaPluginAposGeracao plugin : ServiceLoader.load(CotubaPluginAposGeracao.class)) {
            plugin.aposGeracao(ebook);
        }
    }
}
