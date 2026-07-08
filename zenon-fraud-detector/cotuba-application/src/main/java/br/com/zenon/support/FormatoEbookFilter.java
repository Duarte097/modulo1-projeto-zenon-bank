package br.com.zenon;

import jakarta.enterprise.util.AnnotationLiteral;

public class FormatoEbookFilter extends AnnotationLiteral<FormatoEbookQualifier>  implements FormatoEbookQualifier {

    private final FormatoEbook formato;

    private FormatoEbookFilter(FormatoEbook formato) {
        this.formato = formato;
    }
    
    @Override
    public FormatoEbook value() {
        return formato;
    }

    public FormatoEbookFilter of(FormatoEbook formato) {
        return new FormatoEbookFilter(formato);
    }
}
