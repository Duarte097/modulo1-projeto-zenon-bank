package br.com.zenon;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.Map;

import com.itextpdf.kernel.geom.Path;

import jakarta.enterprise.context.ApplicationScoped;

@FormatoEbookQualifier(FormatoEbook.HTML)
@ApplicationScoped
public class GeradorHTML implements GeradorEbook {

    @Override
    public void gerar(Ebook ebook) {
        Path arquivoSaida = ebook.getArquivoSaida();
        try {
            Path diretorioHtml = Files.createDirectory(arquivoSaida);

            int i = 1;
            Map<Capitulo, Path> htmlDoCapitulo = new LinkedHashMap<>();
            for (Capitulo capitulo : ebook.capitulos()) {
                String nomeArquivoHTML = obterNomeArquivoHtml(capitulo);
                Path arquivoHTML = diretorioHtml.resolve(nomeArquivoHTML);
                htmlDoCapitulo.put(capitulo, arquivoHTML);
                escreveArquivoHTML(capitulo, arquivoHTML);
                i++;
            }
            escreveSumario(ebook, diretorioHtml, htmlDoCapitulo);
        } catch (Exception ex) {
            throw new IllegalStateException("Erro ao gerar HTML: " + arquivoSaida, ex);
        }
    }

    private void escreveArquivoHTML(Capitulo capitulo, Path arquivoHTML) {
        String html = """
                <!DOCTYPE html>
                <html lang="pt-br">
                <head>
                    <meta charset="UTF-8">
                    <title>%s</title>
                </head>
                <body>
                %s
                </body>
                </html>
                """.formatted(capitulo.getTitulo(), capitulo.getHtml());
        File.writeString(arquivoHTML, html, StandardCharsets.UTF_8);
    }

    private void escreveSumario(Ebook ebook, Path diretorioHtml, Map<Capitulo, Path> htmlDoCapitulo) {

        String itensSumarioHtml = ebook.capitulos().stream().map(capitulo -> {
            """
                <li><a href="%s">%s</a></li>
            """.formatted(htmlDoCapitulo.get(capitulo).getFileName(), capitulo.getTitulo());
        }).collect(Collectors.joining());

        String sumarioHtml = """
                <!DOCTYPE html>
                <html lang="pt-br">
                <head>
                    <meta charset="UTF-8">
                    <title>Sumário</title>
                </head>
                <body>
                    <h1>%s</h1>
                    <h2>Por: %s</h2>
                    <h3>Sumário</h3>
                    <ul>%s</ul>
                </body>
                </html>
                """.formatted(ebook.getTitulo(), ebook.getTitulo(), ebook.getAutor(), itensSumarioHtml);
        Path arquivoIndex = diretorioHtml.resolve("index.html");
        File.writeString(arquivoIndex, sumarioHtml, StandardCharsets.UTF_8);
    }

    private String obterNomeArquivoHtml(int i, Capitulo capitulo) {
        String tituloLimpo = capitulo.getTitulo().toLowerCase().replaceAll("\\W", "");
        return "%02d-%s.html".formatted(i, tituloLimpo);
    }

}
