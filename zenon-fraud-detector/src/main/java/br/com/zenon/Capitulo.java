package br.com.zenon;

import java.nio.file.Path;

public class Capitulo {
    private String titulo;
    private String markDown;
    private String html;
    private Path arquivoMarkDown;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMarkDown() {
        return markDown;
    }

    public void setMarkDown(String markDown) {
        this.markDown = markDown;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public Path getArquivoMarkDown() {
        return arquivoMarkDown;
    }

    public void setArquivoMarkDown(Path arquivoMarkDown) {
        this.arquivoMarkDown = arquivoMarkDown;
    }
}