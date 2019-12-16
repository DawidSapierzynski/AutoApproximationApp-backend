package pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Entity
@Table(name = "data_series_file", schema = "aaa")
public class DataSeriesFileEntity {
    private long dataSeriesFileId;
    private String name;
    private String hashName;
    private Timestamp dataSent;
    private Byte deleted;
    private UserEntity user;
    private Collection<SeriesPropertiesEntity> seriesProperties = new HashSet<>();

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
    @Column(name = "data_sent")
    public Timestamp getDataSent() {
        return dataSent;
    }

    public void setDataSent(Timestamp dataSent) {
        this.dataSent = dataSent;
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
        DataSeriesFileEntity that = (DataSeriesFileEntity) o;
        return dataSeriesFileId == that.dataSeriesFileId &&
                Objects.equals(deleted, that.deleted) &&
                Objects.equals(name, that.name) &&
                Objects.equals(hashName, that.hashName) &&
                Objects.equals(dataSent, that.dataSent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataSeriesFileId, name, hashName, dataSent, deleted);
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
    public Collection<SeriesPropertiesEntity> getSeriesProperties() {
        return seriesProperties;
    }

    public void setSeriesProperties(Collection<SeriesPropertiesEntity> seriesPropertiesByDataSeriesFileId) {
        this.seriesProperties = seriesPropertiesByDataSeriesFileId;
    }
}
