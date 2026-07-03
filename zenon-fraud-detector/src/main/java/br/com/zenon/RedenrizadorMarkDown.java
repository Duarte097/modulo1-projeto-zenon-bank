package br.com.zenon;

import java.nio.file.Path;

public interface RedenrizadorMarkDown {
    List<Capitulo> renderizar(List<MarkDown> markDowns);
}