package br.com.zenon;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LeitorPropriedadesEbookArquivo implements LeitorPropriedadesEbook {
    public PropriedadesEbook ler(Path diretorioMD) {

        Path arquivoProperties = diretorioMD.resolve("ebook.properties");

        if(!File.exists(arquivoProperties)){
            throw new IllegalArgumentException("Arquivo ebook.properties não encontrado no diretório: " + diretorioMD);
        }

        Properties proerties = new Properties();
        try(InputStream in = Files.newBufferedReader(arquivoProperties, StandardCharsets.UTF_8)){
            proerties.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o arquivo ebook.properties", e);
        }

        String titulo = proerties.getProperty("cotuba.ebook.titulo");
        validarPropriedade(titulo, "cotuba.ebook.titulo");
        String autor = proerties.getProperty("cotuba.ebook.autor");
        validarPropriedade(autor, "cotuba.ebook.autor");

        return new PropriedadesEbook(titulo, autor);
    }

    private static void validarPropriedade(String valor, String propriedade) {
        if(valor == null || valor.isBlank()){
            throw new IllegalArgumentException("Propriedade " + propriedade + " não encontrada no arquivo ebook.properties");
        }
    }
}
