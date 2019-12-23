package pl.edu.wat.wcy.isi.autoapproximationappbackend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.function.DomainFunction;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.function.polynomials.Polynomial;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.MathematicalFunctionDTO;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.mapper.PolynomialMapper;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.PointXY;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class DownloadService {
    private final Logger logger = LoggerFactory.getLogger(DownloadService.class);

    private PolynomialMapper polynomialMapper;

    public DownloadService(PolynomialMapper polynomialMapper) {
        this.polynomialMapper = polynomialMapper;
    }

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

    public byte[] generateDataSeries(boolean isTrigonometricPolynomial, MathematicalFunctionDTO mathematicalFunctionDTO, int numberPoints) {
        StringBuilder stringBuilder = new StringBuilder();
        Polynomial polynomial = polynomialMapper.mapToPolynomial(mathematicalFunctionDTO.getPolynomialDTO(), isTrigonometricPolynomial);
        logger.debug("{}", polynomial);

        List<PointXY> points = generatePoints(mathematicalFunctionDTO.getDomainFunction(), numberPoints, polynomial);

        stringBuilder.append("//")
                .append(polynomial)
                .append("\n");

        for (PointXY p : points) {
            stringBuilder.append(p.getX())
                    .append(";")
                    .append(p.getY())
                    .append("\n");
        }

        return stringBuilder.toString().getBytes();
    }

    private List<PointXY> generatePoints(DomainFunction domainFunction, int numberPoints, Polynomial polynomial) {
        List<PointXY> points = new ArrayList<>();

        BigDecimal step = BigDecimal.valueOf(domainFunction.getEndInterval() - domainFunction.getBeginningInterval()).divide(BigDecimal.valueOf(numberPoints), RoundingMode.HALF_UP);

        for (BigDecimal x = BigDecimal.valueOf(domainFunction.getBeginningInterval()); x.compareTo(BigDecimal.valueOf(domainFunction.getEndInterval())) < 0; x = x.add(step)) {
            points.add(new PointXY(x.doubleValue(), polynomial.evaluate(x.doubleValue())));
        }

        return points;
    }
}
