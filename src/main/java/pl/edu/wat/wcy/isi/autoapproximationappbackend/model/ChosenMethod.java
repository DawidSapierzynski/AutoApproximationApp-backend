package pl.edu.wat.wcy.isi.autoapproximationappbackend.model;

public class ChosenMethod {
    private Method method;
    private int degree;

    public ChosenMethod(Method method, int degree) {
        this.method = method;
        this.degree = degree;
    }

    public Method getMethod() {
        return method;
    }

    public int getDegree() {
        return degree;
    }
}
