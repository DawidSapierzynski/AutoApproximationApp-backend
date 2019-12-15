package pl.edu.wat.wcy.isi.autoapproximationappbackend.dto;

import lombok.*;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.function.DomainFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MathematicalFunctionDTO {
    private PolynomialDTO polynomialDTO;
    private DomainFunction domainFunction;
}
