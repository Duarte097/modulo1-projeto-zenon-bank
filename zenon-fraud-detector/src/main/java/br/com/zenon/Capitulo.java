package br.com.zenon;

import io.soabase.recordbuilder.core.RecordBuilder;

@RecordBuilder
public record Capitulo(String titulo, MarkDown markDown, String html) {
    
}