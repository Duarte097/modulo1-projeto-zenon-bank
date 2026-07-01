package br.com.zenon;

import org.w3c.dom.Node;
import org.w3c.dom.Text;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.List;
import java.util.stream.Stream;

import javax.swing.text.html.parser.Parser;

public class RedenrizadorMarkDown {

    public List<Capitulo> redenrizar(Path diretorioMDs) {

        var repositorioMarkDowns = new RepositorioMarkDowns();

        List<Capitulo> capitulos = repositorioMarkDowns.buscar(diretorioMDs);

        return capitulos.stream().map(capitulo -> {

            var arquivoMD = capitulo.getArquivoMarkDown();
            var capitulo = new Capitulo();
            Parser parser = Parser.builder().build();
            Node document = null;
            try {
                String markDown = capitulo.getMarkDown();
                document = parser.parse(markDown);
                document.accept(new AbstractVisitor() {
                    @Override
                    public void visit(Heading heading) {
                        if (heading.getLevel() == 1) {
                            // capítulo
                            String tituloDoCapitulo = ((Text) heading.getFirstChild()).getLiteral();
                            capitulo.setTitulo(tituloDoCapitulo);
                            // TODO: usar título do capítulo
                        } else if (heading.getLevel() == 2) {
                            // seção
                        } else if (heading.getLevel() == 3) {
                            // título
                        }
                    }

                });
            } catch (Exception ex) {
                throw new IllegalStateException("Erro ao fazer parse do arquivo " + capitulo.getArquivoMarkDown(), ex);
            }

            try {
                HtmlRenderer renderer = HtmlRenderer.builder().build();
                String html = renderer.render(document);
                capitulo.setHtml(html);
                return capitulo;

            } catch (Exception ex) {
                throw new IllegalStateException(
                        "Erro ao renderizar para HTML o arquivo " + capitulo.getArquivoMarkDown(), ex);
            }
        }).toList();

    }
}
