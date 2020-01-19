package pl.edu.wat.wcy.isi.autoapproximationappbackend.core.chooseMethod;

import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.ChosenMethodDTO;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.service.ChooseMethodService;

import java.util.ArrayList;
import java.util.List;

public class ChooseMethodPrecision3 implements ChooseMethodStrategy {
    private static final double PARAMETER = 1.5;

    @Override
    public List<ChosenMethodDTO> getMethod(boolean periodicity, int size) {
        List<ChosenMethodDTO> chosenMethodDTOS = new ArrayList<>();
        int degree = (int) Math.round(PARAMETER * ChooseMethodService.getDefaultDegree(size));
        return ChooseMethodService.getChosenMethodDTOs(periodicity, size, chosenMethodDTOS, degree);
    }
}
