package pl.edu.wat.wcy.isi.autoapproximationappbackend.core.chooseMethod;

import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.ChosenMethodDTO;

import java.util.List;

public interface ChooseMethodStrategy {
    List<ChosenMethodDTO> getMethod(boolean fastVariation, boolean equidistant, int size);
}
