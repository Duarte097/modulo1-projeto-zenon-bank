package br.com.zenon;

import org.jmolecules.ddd.annotation.Entity;
import org.jmolecules.ddd.annotation.Identity;

@Entity
public record MarkDown( @Identity String nome, String conteudo) {
    
}
