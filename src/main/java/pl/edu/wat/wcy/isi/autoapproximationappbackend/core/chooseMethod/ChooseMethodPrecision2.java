package pl.edu.wat.wcy.isi.autoapproximationappbackend.core.chooseMethod;

import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.ChosenMethodDTO;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.service.ChooseMethodService;

import java.util.ArrayList;
import java.util.List;

public class ChooseMethodPrecision2 implements ChooseMethodStrategy {
    @Override
    public List<ChosenMethodDTO> getMethod(boolean fastVariation, int size) {
        List<ChosenMethodDTO> chosenMethodDTOS = new ArrayList<>();

        int degree = ChooseMethodService.getDefaultDegree(size);
        return ChooseMethodService.getChosenMethodDTOs(fastVariation, size, chosenMethodDTOS, degree);
    }
}
