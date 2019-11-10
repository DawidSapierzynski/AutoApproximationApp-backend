package pl.edu.wat.wcy.isi.autoapproximationappbackend.function;

import pl.edu.wat.wcy.isi.autoapproximationappbackend.polynomial.Polynomial;

public class Function {
    private Polynomial polynomial;
    private DomainFunction domainFunction;

    public Function(Polynomial polynomial, DomainFunction domainFunction) {
        this.polynomial = polynomial;
        this.domainFunction = domainFunction;
    }

    public Polynomial getPolynomial() {
        return polynomial;
    }

    public DomainFunction getDomainFunction() {
        return domainFunction;
    }
}
