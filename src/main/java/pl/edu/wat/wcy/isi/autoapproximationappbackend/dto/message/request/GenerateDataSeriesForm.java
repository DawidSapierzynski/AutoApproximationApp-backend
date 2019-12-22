package pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.message.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.MathematicalFunctionDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenerateDataSeriesForm {
    private MathematicalFunctionDTO mathematicalFunctionDTO;
    private int numberPoints;
    private boolean trigonometricPolynomial;
}
