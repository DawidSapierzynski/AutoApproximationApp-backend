package pl.edu.wat.wcy.isi.autoapproximationappbackend.mapper;

import org.springframework.stereotype.Service;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.PolynomialDTO;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.function.polynomials.Polynomial;

@Service
public class PolynomialMapper {

    public PolynomialDTO mapToPolynomialDTO(Polynomial polynomial) {
        return PolynomialDTO.builder()
                .coefficients(polynomial.getCoefficients())
                .degree(polynomial.getDegree())
                .build();
    }
}
