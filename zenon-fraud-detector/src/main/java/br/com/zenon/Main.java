package br.com.zenon;

<<<<<<< HEAD
import java.nio.file.Path;
import java.util.List;

public class Main {

    void main(String[] args) {
        int exitCode = executar(args);
        if (exitCode != 0) {
            System.exit(exitCode);
        }
    }

    int executar(String[] args) {
        var leitorOpcoesCLI = new LeitorOpcoesCLI();
        leitorOpcoesCLI.ler(args);

        boolean modoVerboso = true;
        try {
            Path diretorioDosMD = leitorOpcoesCLI.getDiretorioDosMD();
            String formato = leitorOpcoesCLI.getFormato();
            Path arquivoDeSaida = leitorOpcoesCLI.getArquivoDeSaida();
            modoVerboso = leitorOpcoesCLI.isModoVerboso();

            var redenrizadorMarkDown = new RedenrizadorMarkDown();
            List<String> htmls = redenrizadorMarkDown.redenrizar(diretorioDosMD);

            if ("pdf".equals(formato)) {

                var geradorPDF = new GeradorPDF();
                geradorPDF.gerarPDF(htmls, arquivoDeSaida);

            } else if ("epub".equals(formato)) {
                var geradorEPUB = new GeradorEPUB();
                geradorEPUB.gerarEPUB(htmls, arquivoDeSaida);

            } else {
                throw new IllegalArgumentException("Formato do ebook inválido: " + formato);
            }

            System.out.println("Arquivo gerado com sucesso: " + arquivoDeSaida);
            return 0;

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            if (modoVerboso) {
                System.err.println();
                ex.printStackTrace();
            }
            return 1;
        }
    }

}
=======
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        IO.println(String.format("Hello and welcome!"));

        for (int i = 1; i <= 5; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
            IO.println("i = " + i);
        }
    }
}
>>>>>>> 78fd68469067bd43bf4bbe0dc80f68516dfae317
