package br.com.zenon.tema;

public class PluginTemaCSS implements CotubaPlugin{

    //

    @Override
    public String aposRenderizador(String html){
        return """
                <style>
                    h1{
                        border-bottom: 1px dashed black;
                        font-size: 3em;
                        font-weight: bolder;
                        font-variant-caps: small-caps;
                    }
                    h2{
                        border-left: 1px solid black;
                        padding-left: 5px;
                        border-bottom: 1px solid black;
                    }
                </style>
                %s
                """.formatted(html);
    }

    @Override
    public String aposGeracao(Ebook ebook){
    }


}
