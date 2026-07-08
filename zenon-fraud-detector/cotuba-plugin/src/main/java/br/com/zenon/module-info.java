module cotuba.plugin{
    requires cotuba.domain;

    exports br.com.zenon.cotuba.plugin;

    uses br.com.zenon.cotuba.plugin.CotubaPluginAposGeracao;
    uses br.com.zenon.cotuba.plugin.CotubaPluginAposRedenrizacao; 
}