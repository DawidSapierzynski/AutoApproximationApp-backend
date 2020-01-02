package pl.edu.wat.wcy.isi.autoapproximationappbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.PointXY;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApproximationDTO {
    private List<MathematicalFunctionDTO> mathematicalFunctionDTOs;
    private List<PointXY> points;
    private double absoluteError;
}
