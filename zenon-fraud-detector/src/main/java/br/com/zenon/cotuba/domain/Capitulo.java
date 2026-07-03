package br.com.zenon;

import org.jmolecules.ddd.annotation.Entity;
import org.jmolecules.ddd.annotation.Identity;

import io.soabase.recordbuilder.core.RecordBuilder;

@Entity
@RecordBuilder
public record Capitulo(@Identity String titulo, MarkDown markDown, String html) {
    
}