package pl.edu.wat.wcy.isi.autoapproximationappbackend.core.chooseMethod;

import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.ChosenMethodDTO;

import java.util.List;

public class ChooseMethodContext {
    private ChooseMethodStrategy chooseMethodStrategy;

    public void setChooseMethodStrategy(ChooseMethodStrategy chooseMethodStrategy) {
        this.chooseMethodStrategy = chooseMethodStrategy;
    }

    public List<ChosenMethodDTO> getMethod(boolean fastVariation, boolean equidistant, int size) {
        return chooseMethodStrategy.getMethod(fastVariation, equidistant, size);
    }
}
