package br.com.zenon;

import java.nio.file.Path;
import java.util.List;

import org.jmolecules.ddd.annotation.Repository;

@Repository
public interface RepositorioMarkDown {
    List<MarkDown> buscar(Path diretorioDosMD);
}
