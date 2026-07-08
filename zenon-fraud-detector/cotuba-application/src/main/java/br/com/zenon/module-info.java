open module cotuba.application {
    requires cotuba.domain;
    requires cotuba.plugin;

    requires org.jmolecules.architecture.hexagonal;
    requires org.jmolecules.ddd;
    requires weld.se.shaded;
    

    exports br.com.zenon.cotuba.ports.out;
    exports br.com.zenon.cotuba.ports.in;
    exports br.com.zenon.cotuba.dto;
    exports br.com.zenon.cotuba.support;

    uses br.com.zenon.cotuba.plugin.CotubaPluginAposGeracao;
    
}