package br.com.zenon;

import java.awt.print.Book;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import jakarta.enterprise.context.ApplicationScoped;

@FormatoEbookQualifier(FormatoEbook.EPUB)
@ApplicationScoped
public class GeradorEPUB implements GeradorEbook {

    public void gerar(Ebook ebook) {

        List<Capitulo> capitulos = ebook.getCapitulos();
        Path arquivoSaida = ebook.getArquivoSaida();

        try {
            var epub = new Book();

            //TODO: definir título e autor para o livro
            epub.getMetadata().addTitle(ebook.getTitulo());
            epub.getMetadata().addAuthor(new Author(ebook.getAutor()));

            boolean[] ehPrimeiroCapitulo = {true};


            capitulos.forEach(capitulo -> {
                String html = capitulo.getHtml();
                String tituloDoCapitulo = capitulo.getTitulo();

                try {
                    StringWriter stringWriter = new StringWriter();
                    XMLStreamWriter writer = XMLOutputFactory.newInstance().createXMLStreamWriter(stringWriter);


                    writer.writeStartElement("html");
                    writer.writeDefaultNamespace("http://www.w3.org/1999/xhtml");

                    writer.writeStartElement("head");
                    writer.writeStartElement("title");
                    writer.writeCharacters(ebook.getTitulo());
                    writer.writeEndElement(); // title
                    writer.writeEndElement(); // head

                    writer.writeStartElement("body");
                    writer.writeCharacters("");
                    writer.flush();
                    stringWriter.write(html);

                    writer.writeEndElement(); // body
                    writer.writeEndElement(); // html

                    writer.close();

                }catch (Exception ex) {
                    throw new IllegalStateException("Erro ao criar capitulo do epub: " + tituloDoCapitulo, ex);
                }

                var chapter = new Resource(stringWriter.toString().getBytes(), MediatypeService.XHTML);
                epub.addSection(tituloDoCapitulo, chapter);

                if (ehPrimeiroCapitulo[0]) {
                    epub.getGuide().addReference(new GuideReference(chapter, "text", "Start Reading"));
                    ehPrimeiroCapitulo[0] = false;
                }
            })


            var epubWriter = new EpubWriter();

            try {
                epubWriter.write(epub, Files.newOutputStream(arquivoSaida));
            } catch (IOException ex) {
                throw new IllegalStateException("Erro ao criar arquivo EPUB: " + arquivoSaida.toAbsolutePath(), ex);
            }

        } catch (
                Exception ex) {
            throw new IllegalStateException("Erro ao gerar EPUB: " + arquivoSaida.toAbsolutePath(), ex);
        }
    }
}
