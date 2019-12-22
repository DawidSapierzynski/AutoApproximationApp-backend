package pl.edu.wat.wcy.isi.autoapproximationappbackend.mapper;

import org.springframework.stereotype.Service;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.function.polynomials.AlgebraicPolynomial;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.function.polynomials.Polynomial;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.function.polynomials.TrigonometricPolynomial;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.PolynomialDTO;

@Service
public class PolynomialMapper {

    public PolynomialDTO mapToPolynomialDTO(Polynomial polynomial) {
        return PolynomialDTO.builder()
                .coefficients(polynomial.getCoefficients())
                .degree(polynomial.getDegree())
                .build();
    }

    public Polynomial mapToPolynomial(PolynomialDTO polynomialDTO, boolean isTrigonometricPolynomial) {
        if (isTrigonometricPolynomial) {
            return new TrigonometricPolynomial(polynomialDTO.getCoefficients());
        } else {
            return new AlgebraicPolynomial(polynomialDTO.getCoefficients());
        }
    }
}
