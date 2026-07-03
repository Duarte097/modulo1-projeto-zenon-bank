package br.com.zenon;

import com.itextpdf.layout.element.List;

public interface RepositorioMarkDown {
    List<MarkDown> buscar(Path diretorioDosMD)
}
