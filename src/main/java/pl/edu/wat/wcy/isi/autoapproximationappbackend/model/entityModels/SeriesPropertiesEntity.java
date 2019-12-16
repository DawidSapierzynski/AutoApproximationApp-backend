package pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels;

import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.PointXY;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "series_properties", schema = "aaa")
public class SeriesPropertiesEntity {
    private Long seriesPropertiesId;
    private Integer size;
    private Double fastVariationPolynomial;
    private Double fastVariationTrigonometric;
    private Byte fastVariation;
    private Double variance;
    private Double standardDeviation;
    private Integer precisionApproximation;
    private Timestamp dateCreate;
    private Byte deleted = (byte) 0;
    private DataSeriesFileEntity dataSeriesFile;
    private UserEntity user;
    private List<PointXY> points;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "series_properties_id")
    public Long getSeriesPropertiesId() {
        return seriesPropertiesId;
    }

    public void setSeriesPropertiesId(Long seriesPropertiesId) {
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
    @Column(name = "date_create")
    public Timestamp getDataCreate() {
        return dateCreate;
    }

    public void setDataCreate(Timestamp dataCreate) {
        this.dateCreate = dataCreate;
    }

    @Basic
    @Column(name = "is_deleted")
    public Byte getDeleted() {
        return deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeriesPropertiesEntity that = (SeriesPropertiesEntity) o;
        return Objects.equals(seriesPropertiesId, that.seriesPropertiesId) &&
                Objects.equals(size, that.size) &&
                Objects.equals(fastVariationPolynomial, that.fastVariationPolynomial) &&
                Objects.equals(fastVariationTrigonometric, that.fastVariationTrigonometric) &&
                Objects.equals(fastVariation, that.fastVariation) &&
                Objects.equals(variance, that.variance) &&
                Objects.equals(standardDeviation, that.standardDeviation) &&
                Objects.equals(precisionApproximation, that.precisionApproximation) &&
                Objects.equals(dateCreate, that.dateCreate) &&
                Objects.equals(deleted, that.deleted) &&
                Objects.equals(dataSeriesFile, that.dataSeriesFile) &&
                Objects.equals(user, that.user) &&
                Objects.equals(points, that.points);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seriesPropertiesId, size, fastVariationPolynomial, fastVariationTrigonometric, fastVariation, variance, standardDeviation, precisionApproximation, dateCreate, deleted, dataSeriesFile, user, points);
    }

    @ManyToOne
    @JoinColumn(name = "data_series_file_id", referencedColumnName = "data_series_file_id")
    public DataSeriesFileEntity getDataSeriesFile() {
        return dataSeriesFile;
    }

    public void setDataSeriesFile(DataSeriesFileEntity dataSeriesFileByDataSeriesFileId) {
        this.dataSeriesFile = dataSeriesFileByDataSeriesFileId;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity userByUserId) {
        this.user = userByUserId;
    }

    @Transient
    public List<PointXY> getPoints() {
        return points;
    }

    public void setPoints(List<PointXY> points) {
        this.points = points;
    }
}
