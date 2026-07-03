package br.com.zenon;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.List;
import java.util.stream.Stream;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RepositorioMarkDownsDiretorio implements RepositorioMarkDown {
    public List<MarkDown> buscar(Path diretorioDosMD) {
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**/*.md");
        try (Stream<Path> streamMDs = Files.list(diretorioMD)) {
            List<Path> arquivosMD = streamMDs
                    .filter(matcher::matches)
                    .sorted()
                    .toList();

            if (arquivosMD.isEmpty()) {
                throw new IllegalStateException(
                        "Não foram encontrados capítulos (arquivos .md) no diretório: " + diretorioMD.toAbsolutePath());
            }

            return arquivosMD.stream().map(arquivoMD -> {
                try{
                    String conteudo = Files.readString(arquivoMD);
                    return new MarkDown(conteudo, arquivoMD)
                } catch (IOException ex) {
                    throw new IllegalStateException("Erro ao ler arquivos .md em " + arquivoMD, ex);
                }
        }).toList();

        } catch (IOException ex) {
            throw new IllegalStateException("Erro tentando encontrar arquivos .md em " + arquivoMD, ex);
        }
    }
}
