package pl.edu.wat.wcy.isi.autoapproximationappbackend.service;

import org.springframework.stereotype.Service;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.MathematicalFunctionDTO;

import java.util.List;

@Service
public class DownloadService {
    public byte[] getApproximationResult(List<MathematicalFunctionDTO> mathematicalFunctionDTOs) {
        StringBuilder stringBuilder = new StringBuilder();
        List<Double> coefficients;

        for (MathematicalFunctionDTO m : mathematicalFunctionDTOs) {
            stringBuilder.append(m.getDomainFunction()).append("\n");
            coefficients = m.getPolynomialDTO().getCoefficients();
            for (int i = 0; i < coefficients.size(); i++) {
                stringBuilder.append("a")
                        .append(i)
                        .append("=")
                        .append(coefficients.get(i))
                        .append("\n");
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString().getBytes();
    }
}
