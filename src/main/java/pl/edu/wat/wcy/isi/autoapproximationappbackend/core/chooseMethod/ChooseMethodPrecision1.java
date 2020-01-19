package pl.edu.wat.wcy.isi.autoapproximationappbackend.core.chooseMethod;

import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.ChosenMethodDTO;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.Method;

import java.util.ArrayList;
import java.util.List;

public class ChooseMethodPrecision1 implements ChooseMethodStrategy {
    @Override
    public List<ChosenMethodDTO> getMethod(boolean periodicity, int size) {
        List<ChosenMethodDTO> chosenMethodDTOS = new ArrayList<>();

        if (periodicity) {
            chosenMethodDTOS.add(new ChosenMethodDTO(Method.TRIGONOMETRICAPPROXIMATION, 1));
        } else {
            chosenMethodDTOS.add(new ChosenMethodDTO(Method.POLYNOMIALAPPROXIMATION, 1));
        }
        
        return chosenMethodDTOS;
    }
}
