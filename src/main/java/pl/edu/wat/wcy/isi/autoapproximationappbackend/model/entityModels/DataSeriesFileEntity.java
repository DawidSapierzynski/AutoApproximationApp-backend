package pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels;

import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.PointXY;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "data_series_file", schema = "aaa")
public class DataSeriesFileEntity {
    private long dataSeriesFileId;
    private String name;
    private String hashName;
    private Timestamp dateSent;
    private Byte deleted = (byte) 0;
    private UserEntity user;
    private Integer size;
    private Double fastVariationPolynomial;
    private Double fastVariationTrigonometric;
    private Byte fastVariation;
    private Double variance;
    private Double standardDeviation;
    private Byte equidistant;
    private Collection<ApproximationPropertiesEntity> approximationProperties = new HashSet<>();
    private List<PointXY> points;
    private List<PointXY> artefacts;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "data_series_file_id")
    public Long getDataSeriesFileId() {
        return dataSeriesFileId;
    }

    public void setDataSeriesFileId(long dataSeriesFileId) {
        this.dataSeriesFileId = dataSeriesFileId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "hash_name")
    public String getHashName() {
        return hashName;
    }

    public void setHashName(String hashName) {
        this.hashName = hashName;
    }

    @Basic
    @Column(name = "date_sent")
    public Timestamp getDateSent() {
        return dateSent;
    }

    public void setDateSent(Timestamp dateSent) {
        this.dateSent = dateSent;
    }

    @Basic
    @Column(name = "is_deleted")
    public Byte getDeleted() {
        return deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
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
    @Column(name = "is_equidistant")
    public Byte getEquidistant() {
        return equidistant;
    }

    public void setEquidistant(Byte equidistant) {
        this.equidistant = equidistant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataSeriesFileEntity that = (DataSeriesFileEntity) o;
        return dataSeriesFileId == that.dataSeriesFileId &&
                Objects.equals(deleted, that.deleted) &&
                Objects.equals(name, that.name) &&
                Objects.equals(hashName, that.hashName) &&
                Objects.equals(dateSent, that.dateSent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataSeriesFileId, name, hashName, dateSent, deleted);
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity userByUserId) {
        this.user = userByUserId;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "dataSeriesFile")
    public Collection<ApproximationPropertiesEntity> getApproximationProperties() {
        return approximationProperties;
    }

    public void setApproximationProperties(Collection<ApproximationPropertiesEntity> approximationPropertiesByDataSeriesFileId) {
        this.approximationProperties = approximationPropertiesByDataSeriesFileId;
    }

    @Transient
    public synchronized List<PointXY> getPoints() {
        return points;
    }

    public synchronized void setPoints(List<PointXY> points) {
        this.points = points;
    }

    @Transient
    public synchronized List<PointXY> getArtefacts() {
        return artefacts;
    }

    public synchronized void setArtefacts(List<PointXY> artefacts) {
        this.artefacts = artefacts;
    }
}
