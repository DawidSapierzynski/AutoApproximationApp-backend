package pl.edu.wat.wcy.isi.autoapproximationappbackend.core.approximation;

import Jama.Matrix;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.PointXY;

import java.util.List;

import static pl.edu.wat.wcy.isi.autoapproximationappbackend.core.function.polynomials.TrigonometricPolynomial.chooseTrigonometricMaxDegree;

public class TrigonometricInterpolation extends TrigonometricApproximationAbstract {

    public TrigonometricInterpolation(List<PointXY> points) {
        super(points);
        setDegree(chooseTrigonometricMaxDegree(points.size()));
    }

    @Override
    protected Matrix getMatrixA(Matrix matrixX, Matrix matrixY) {
        return matrixX.lu().solve(matrixY);
    }


}
