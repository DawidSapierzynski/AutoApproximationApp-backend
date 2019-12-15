package pl.edu.wat.wcy.isi.autoapproximationappbackend.function;

import pl.edu.wat.wcy.isi.autoapproximationappbackend.function.polynomials.Polynomial;

public class MathematicalFunction {
    private Polynomial polynomial;
    private DomainFunction domainFunction;

    public MathematicalFunction(Polynomial polynomial, DomainFunction domainFunction) {
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
