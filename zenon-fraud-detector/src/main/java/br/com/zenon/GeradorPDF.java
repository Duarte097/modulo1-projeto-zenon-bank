package br.com.zenon;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class GeradorPDF {

    public void gerarPDF(Ebook ebook) {

        List<Capitulo> capitulos = ebook.getCapitulos();
        Path arquivoSaida = ebook.getArquivoSaida();

        try (var writer = new PdfWriter(Files.newOutputStream(arquivoSaida));
             var pdf = new PdfDocument(writer);
             var pdfDocument = new Document(pdf)) {

            pdf.getDocumentInfo().setTitle(ebook.getTitulo());
            pdf.getDocumentInfo().setAuthor(ebook.getAutor());

            capitulos.forEach(capitulo -> {
                String html = capitulo.getHtml();

                List<IElement> convertToElements = HtmlConverter.convertToElements(html);

                if (pdf.getNumberOfPages() == 0) {
                    pdf.addNewPage();
                }
                PdfOutline rootOutline = pdf.getOutlines(false);
                if (rootOutline == null) {
                    pdf.initializeOutlines();
                    rootOutline = pdf.getOutlines(false);
                }

                String tituloDoCapitulo = capitulo.getTitulo();
                PdfOutline chapterOutline = rootOutline.addOutline(tituloDoCapitulo);
                chapterOutline.addDestination(PdfExplicitDestination.createFit(pdf.getLastPage()));

                for (IElement element : convertToElements) {
                    pdfDocument.add((IBlockElement) element);
                }
                // TODO: não adicionar página depois do último capítulo
                pdfDocument.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

            });

        } catch (Exception ex) {
            throw new IllegalStateException("Erro ao gerar PDF: " + arquivoSaida.toAbsolutePath(), ex);
        }
    }
}
