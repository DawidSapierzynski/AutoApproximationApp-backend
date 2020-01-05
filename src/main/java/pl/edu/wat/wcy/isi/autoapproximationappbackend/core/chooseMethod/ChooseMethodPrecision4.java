package pl.edu.wat.wcy.isi.autoapproximationappbackend.core.chooseMethod;

import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.ChosenMethodDTO;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.service.ChooseMethodService;

import java.util.ArrayList;
import java.util.List;

public class ChooseMethodPrecision4 implements ChooseMethodStrategy {
    @Override
    public List<ChosenMethodDTO> getMethod(boolean fastVariation, boolean equidistant, int size) {
        List<ChosenMethodDTO> chosenMethodDTOS = new ArrayList<>();

        int degree = 2 * ChooseMethodService.getDefaultDegree(size);
        return ChooseMethodService.getChosenMethodDTOs(fastVariation, equidistant, size, chosenMethodDTOS, degree);
    }
}
