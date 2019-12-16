package pl.edu.wat.wcy.isi.autoapproximationappbackend.core.chooseMethod;

import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.approximation.TrigonometricInterpolation;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.ChosenMethodDTO;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.Method;

import java.util.ArrayList;
import java.util.List;

public class ChooseMethodPrecision5 implements ChooseMethodStrategy {
    @Override
    public List<ChosenMethodDTO> getMethod(boolean fastVariation, int size) {
        List<ChosenMethodDTO> chosenMethodDTOS = new ArrayList<>();

        if (fastVariation) {
            chosenMethodDTOS.add(new ChosenMethodDTO(Method.TRIGONOMETRICINTERPOLATION, TrigonometricInterpolation.chooseTrigonometricDegree(size)));
        } else {
            chosenMethodDTOS.add(new ChosenMethodDTO(Method.NEWTONINTERPOLATION, size - 1));
        }
        chosenMethodDTOS.add(new ChosenMethodDTO(Method.SPLINEINTERPOLATION, 3));

        return chosenMethodDTOS;
    }
}
