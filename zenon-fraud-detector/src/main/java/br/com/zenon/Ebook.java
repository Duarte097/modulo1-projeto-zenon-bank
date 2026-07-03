package br.com.zenon;

import java.util.List;

import com.itextpdf.kernel.geom.Path;

import io.soabase.recordbuilder.core.RecordBuilder;

@RecordBuilder
public record Ebook(String titulo, String autor, FormatoEbook formato, List<Capitulo> capitulos, Path arquivoSaida) {

}