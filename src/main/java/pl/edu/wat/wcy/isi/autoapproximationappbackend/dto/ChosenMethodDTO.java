package pl.edu.wat.wcy.isi.autoapproximationappbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.Method;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChosenMethodDTO implements Serializable {
    private Method method;
    private int degree;
    private boolean isUsed;

    public ChosenMethodDTO(Method method, int degree) {
        this.method = method;
        this.degree = degree;
        this.isUsed = false;
    }
}
