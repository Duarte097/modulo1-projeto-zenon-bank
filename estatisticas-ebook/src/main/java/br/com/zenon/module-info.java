module estatisticas.ebook {
    requires cotuba.domain;
    requires cotuba.plugin;

    requires org.jsoup;

    provides br.com.zenon.cotuba.plugin.CotubaPluginAposGeracao with br.com.zenon.estatisticas.PluginEstatisticas;
}
