package pl.edu.wat.wcy.isi.autoapproximationappbackend.entityModels;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "data_series_file", schema = "aaa", catalog = "")
public class DataSeriesFileEntity {
    private int dataSeriesFileId;
    private String name;
    private String hashName;
    private Timestamp dataSent;
    private byte deleted;
    private UserEntity userByUserId;
    private Collection<SeriesPropertiesEntity> seriesPropertiesByDataSeriesFileId;

    @Id
    @Column(name = "data_series_file_id")
    public int getDataSeriesFileId() {
        return dataSeriesFileId;
    }

    public void setDataSeriesFileId(int dataSeriesFileId) {
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
        DataSeriesFileEntity that = (DataSeriesFileEntity) o;
        return dataSeriesFileId == that.dataSeriesFileId &&
                deleted == that.deleted &&
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
    public UserEntity getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(UserEntity userByUserId) {
        this.userByUserId = userByUserId;
    }

    @OneToMany(mappedBy = "dataSeriesFileByDataSeriesFileId")
    public Collection<SeriesPropertiesEntity> getSeriesPropertiesByDataSeriesFileId() {
        return seriesPropertiesByDataSeriesFileId;
    }

    public void setSeriesPropertiesByDataSeriesFileId(Collection<SeriesPropertiesEntity> seriesPropertiesByDataSeriesFileId) {
        this.seriesPropertiesByDataSeriesFileId = seriesPropertiesByDataSeriesFileId;
    }
}
