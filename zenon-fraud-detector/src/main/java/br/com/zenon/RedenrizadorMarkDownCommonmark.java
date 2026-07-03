package br.com.zenon;

import org.w3c.dom.Node;
import org.w3c.dom.Text;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.List;
import java.util.stream.Stream;

import javax.swing.text.html.parser.Parser;

@ApplicationScoped
public class RedenrizadorMarkDownCommonmark implements RedenrizadorMarkDown {

    @Override
    public List<Capitulo> renderizar(List<MarkDown> markDowns) {
        return markDowns.stream().map(markDown -> {

            var capitulo = CapituloBuilder.builder();
            capitulo.markDown(markDown);
            Parser parser = Parser.builder().build();
            Node document = null;
            try {
                document = parser.parse(markDown.conteudo());
                document.accept(new AbstractVisitor() {
                    @Override
                    public void visit(Heading heading) {
                        if (heading.getLevel() == 1) {
                            // capítulo
                            String tituloDoCapitulo = ((Text) heading.getFirstChild()).getLiteral();
                            capitulo.titulo(tituloDoCapitulo);
                            // TODO: usar título do capítulo
                        } else if (heading.getLevel() == 2) {
                            // seção
                        } else if (heading.getLevel() == 3) {
                            // título
                        }
                    }

                });
            } catch (Exception ex) {
                throw new IllegalStateException("Erro ao fazer parse do arquivo " +  markDown.arquivo(), ex);
            }

            try {
                HtmlRenderer renderer = HtmlRenderer.builder().build();
                String html = renderer.render(document);
                capitulo.html(html);

            } catch (Exception ex) {
                throw new IllegalStateException(
                        "Erro ao renderizar para HTML o arquivo " + markDown.arquivo(), ex);
            }


            return capitulo.build();
        }).toList();

    }
}
