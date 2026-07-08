package br.com.zenon;

import com.itextpdf.kernel.geom.Path;

/**
 * PropriedadesEbook
 */
public record PropriedadesEbook(String titulo, String autor) {
    PropriedadesEbook ler(Path diretorioMD);
}
