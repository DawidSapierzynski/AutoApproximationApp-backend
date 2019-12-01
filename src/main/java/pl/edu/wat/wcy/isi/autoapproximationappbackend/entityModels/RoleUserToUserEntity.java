package pl.edu.wat.wcy.isi.autoapproximationappbackend.entityModels;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "role_user_to_user", schema = "aaa", catalog = "")
public class RoleUserToUserEntity {
    private int roleUserToUserId;
    private UserEntity userByUserId;
    private RoleUserEntity roleUserByRoleUserId;

    @Id
    @Column(name = "role_user_to_user_id")
    public int getRoleUserToUserId() {
        return roleUserToUserId;
    }

    public void setRoleUserToUserId(int roleUserToUserId) {
        this.roleUserToUserId = roleUserToUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleUserToUserEntity that = (RoleUserToUserEntity) o;
        return roleUserToUserId == that.roleUserToUserId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleUserToUserId);
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    public UserEntity getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(UserEntity userByUserId) {
        this.userByUserId = userByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "role_user_id", referencedColumnName = "role_user_id", nullable = false)
    public RoleUserEntity getRoleUserByRoleUserId() {
        return roleUserByRoleUserId;
    }

    public void setRoleUserByRoleUserId(RoleUserEntity roleUserByRoleUserId) {
        this.roleUserByRoleUserId = roleUserByRoleUserId;
    }
}
