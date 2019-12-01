package pl.edu.wat.wcy.isi.autoapproximationappbackend.entityModels;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "series_properties", schema = "aaa", catalog = "")
public class SeriesPropertiesEntity {
    private int seriesPropertiesId;
    private Integer size;
    private Double fastVariationPolynomial;
    private Double fastVariationTrigonometric;
    private Byte fastVariation;
    private Double variance;
    private Double standardDeviation;
    private Integer precisionApproximation;
    private byte deleted;
    private DataSeriesFileEntity dataSeriesFileByDataSeriesFileId;
    private UserEntity userByUserId;

    @Id
    @Column(name = "series_properties_id")
    public int getSeriesPropertiesId() {
        return seriesPropertiesId;
    }

    public void setSeriesPropertiesId(int seriesPropertiesId) {
        this.seriesPropertiesId = seriesPropertiesId;
    }

    @Basic
    @Column(name = "size")
    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Basic
    @Column(name = "fast_variation_polynomial")
    public Double getFastVariationPolynomial() {
        return fastVariationPolynomial;
    }

    public void setFastVariationPolynomial(Double fastVariationPolynomial) {
        this.fastVariationPolynomial = fastVariationPolynomial;
    }

    @Basic
    @Column(name = "fast_variation_trigonometric")
    public Double getFastVariationTrigonometric() {
        return fastVariationTrigonometric;
    }

    public void setFastVariationTrigonometric(Double fastVariationTrigonometric) {
        this.fastVariationTrigonometric = fastVariationTrigonometric;
    }

    @Basic
    @Column(name = "fast_variation")
    public Byte getFastVariation() {
        return fastVariation;
    }

    public void setFastVariation(Byte fastVariation) {
        this.fastVariation = fastVariation;
    }

    @Basic
    @Column(name = "variance")
    public Double getVariance() {
        return variance;
    }

    public void setVariance(Double variance) {
        this.variance = variance;
    }

    @Basic
    @Column(name = "standard_deviation")
    public Double getStandardDeviation() {
        return standardDeviation;
    }

    public void setStandardDeviation(Double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    @Basic
    @Column(name = "precision_approximation")
    public Integer getPrecisionApproximation() {
        return precisionApproximation;
    }

    public void setPrecisionApproximation(Integer precisionApproximation) {
        this.precisionApproximation = precisionApproximation;
    }

    @Basic
    @Column(name = "deleted")
    public byte getDeleted() {
        return deleted;
    }

    public void setDeleted(byte deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeriesPropertiesEntity that = (SeriesPropertiesEntity) o;
        return seriesPropertiesId == that.seriesPropertiesId &&
                deleted == that.deleted &&
                Objects.equals(size, that.size) &&
                Objects.equals(fastVariationPolynomial, that.fastVariationPolynomial) &&
                Objects.equals(fastVariationTrigonometric, that.fastVariationTrigonometric) &&
                Objects.equals(fastVariation, that.fastVariation) &&
                Objects.equals(variance, that.variance) &&
                Objects.equals(standardDeviation, that.standardDeviation) &&
                Objects.equals(precisionApproximation, that.precisionApproximation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seriesPropertiesId, size, fastVariationPolynomial, fastVariationTrigonometric, fastVariation, variance, standardDeviation, precisionApproximation, deleted);
    }

    @ManyToOne
    @JoinColumn(name = "data_series_file_id", referencedColumnName = "data_series_file_id")
    public DataSeriesFileEntity getDataSeriesFileByDataSeriesFileId() {
        return dataSeriesFileByDataSeriesFileId;
    }

    public void setDataSeriesFileByDataSeriesFileId(DataSeriesFileEntity dataSeriesFileByDataSeriesFileId) {
        this.dataSeriesFileByDataSeriesFileId = dataSeriesFileByDataSeriesFileId;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    public UserEntity getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(UserEntity userByUserId) {
        this.userByUserId = userByUserId;
    }
}
