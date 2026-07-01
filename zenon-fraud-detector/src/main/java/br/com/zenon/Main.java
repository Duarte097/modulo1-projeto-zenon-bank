package br.com.zenon;

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
            ParametrosCotuba parametros = leitorOpcoesCLI.ler(args);
        
            modoVerboso = parametros.isModoVerboso();
            var cotubaService = new CotubaService();
            cotubaService.executar(parametros);

            System.out.println("Arquivo gerado com sucesso: " + parametros.getArquivoDeSaida());
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
