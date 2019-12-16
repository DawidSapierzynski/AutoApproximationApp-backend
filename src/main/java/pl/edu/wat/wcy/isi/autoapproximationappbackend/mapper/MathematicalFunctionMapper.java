package pl.edu.wat.wcy.isi.autoapproximationappbackend.mapper;

import org.springframework.stereotype.Service;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.MathematicalFunctionDTO;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.function.MathematicalFunction;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MathematicalFunctionMapper {

    private final PolynomialMapper polynomialMapper;

    public MathematicalFunctionMapper(PolynomialMapper polynomialMapper) {
        this.polynomialMapper = polynomialMapper;
    }

    public List<MathematicalFunctionDTO> mapToMathematicalFunctionDTOs(List<MathematicalFunction> mathematicalFunctions) {
        return mathematicalFunctions.stream().map(this::mapToMathematicalFunctionDTO).collect(Collectors.toList());
    }

    public MathematicalFunctionDTO mapToMathematicalFunctionDTO(MathematicalFunction mathematicalFunction) {
        return MathematicalFunctionDTO.builder()
                .polynomialDTO(polynomialMapper.mapToPolynomialDTO(mathematicalFunction.getPolynomial()))
                .domainFunction(mathematicalFunction.getDomainFunction())
                .build();
    }
}
