

@AnalyzeClasses(packages = "br.com.zenon.cotuba")
public class ArchitectureTest {

    @ArchTest
    static final ArchRule hexagonal = JMoleculesArchitectureRules.ensureHexagonalArchitecture();

    @ArchTest
    static final ArchRule ddd = JMoleculesDddRules.all();

    @ArchTest
    static final ArchRule noCycles = slices().matching("br.com.zenon.cotuba.(*)..").should().beFreeOfCycles();
}
