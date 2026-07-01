package br.com.zenon;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class LeitorPropriedadesEbook {
    public void ler(Path diretorioMD, Ebook ebook) {

        Path arquivoProperties = diretorioMD.resolve("ebook.properties");

        if(!File.exists(arquivoProperties)){
            throw new IllegalArgumentException("Arquivo ebook.properties não encontrado no diretório: " + diretorioMD);
        }

        Properties proerties = new Properties();
        try(InputStream in = Files.newInputStream(arquivoProperties)){
            proerties.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o arquivo ebook.properties", e);
        }

        String titulo = proerties.getProperty("cotuba.ebook.titulo");
        validarPropriedade(titulo, "cotuba.ebook.titulo");
        String autor = proerties.getProperty("cotuba.ebook.autor");
        validarPropriedade(autor, "cotuba.ebook.autor");

        ebook.setTitulo(titulo);
        ebook.setAutor(autor);
    }

    private static void validarPropriedade(String valor, String propriedade) {
        if(valor == null || valor.isBlank()){
            throw new IllegalArgumentException("Propriedade " + propriedade + " não encontrada no arquivo ebook.properties");
        }
    }
}
