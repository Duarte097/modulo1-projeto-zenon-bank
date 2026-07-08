package br.com.zenon;

import java.util.List;

import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Identity;

import com.itextpdf.kernel.geom.Path;

import io.soabase.recordbuilder.core.RecordBuilder;

@AggregateRoot
@RecordBuilder
public record Ebook(@Identity String titulo, String autor, FormatoEbook formato, List<Capitulo> capitulos, Path arquivoSaida) {

}