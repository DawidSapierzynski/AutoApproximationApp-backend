package pl.edu.wat.wcy.isi.autoapproximationappbackend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.core.chooseMethod.*;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.ChosenMethodDTO;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.SeriesPropertiesDTO;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.Method;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChooseMethodService {
    private final Logger logger = LoggerFactory.getLogger(ChooseMethodService.class);

    private static final Map<Integer, ChooseMethodStrategy> chooseMethodStrategyMap = new HashMap<>() {{
        put(1, new ChooseMethodPrecision1());
        put(2, new ChooseMethodPrecision2());
        put(3, new ChooseMethodPrecision3());
        put(4, new ChooseMethodPrecision4());
        put(5, new ChooseMethodPrecision5());
    }};

    public List<ChosenMethodDTO> selectMethods(SeriesPropertiesDTO seriesPropertiesDTO) {
        ChooseMethodContext chooseMethodContext = new ChooseMethodContext();

        chooseMethodContext.setChooseMethodStrategy(chooseMethodStrategyMap.get(seriesPropertiesDTO.getPrecision()));
        List<ChosenMethodDTO> chosenMethodDTOS = chooseMethodContext.getMethod(seriesPropertiesDTO.isFastVariation(), seriesPropertiesDTO.getSize());

        logger.debug("Dobrane metody: {}", chosenMethodDTOS.stream().map(ChosenMethodDTO::getMethod).collect(Collectors.toList()));
        return chosenMethodDTOS;
    }

    public static int getDefaultDegree(int size) {
        return (int) (Math.ceil(Math.log(size)));
    }

    public static List<ChosenMethodDTO> getChosenMethodDTOs(boolean fastVariation, int size, List<ChosenMethodDTO> chosenMethodDTOS, int degree) {
        if (fastVariation) {
            if (degree > size / 2) {
                degree = size / 2;
            }
            chosenMethodDTOS.add(new ChosenMethodDTO(Method.TRIGONOMETRICAPPROXIMATION, degree));
        } else {
            chosenMethodDTOS.add(new ChosenMethodDTO(Method.POLYNOMIALAPPROXIMATION, degree));
        }

        return chosenMethodDTOS;
    }
}
