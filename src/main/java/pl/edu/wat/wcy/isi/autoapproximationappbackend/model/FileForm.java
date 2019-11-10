package pl.edu.wat.wcy.isi.autoapproximationappbackend.model;

import java.io.File;

public class FileForm {
    private File seriesDatesFile;
    private int precision;

    public File getSeriesDatesFile() {
        return seriesDatesFile;
    }

    public void setSeriesDatesFile(File seriesDatesFile) {
        this.seriesDatesFile = seriesDatesFile;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }
}
