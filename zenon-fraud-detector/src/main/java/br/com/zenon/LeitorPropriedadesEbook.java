package br.com.zenon;

import java.nio.file.Path;

public interface LeitorPropriedadesEbook {
    void ler(Path diretorioMD, EbookBuilder ebookBuilder);
}