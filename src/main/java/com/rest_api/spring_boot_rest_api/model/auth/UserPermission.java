package com.rest_api.spring_boot_rest_api.model.auth;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

//@Entity
//@Table(name = "user_permission")
public class UserPermission implements Serializable {
//    @Serial
//    private static final long serialVersionUID = 1L;
//
//    @EmbeddedId
//    private Id id = new Id();
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @MapsId("idUser")
//    @JoinColumn(name = "id_user", nullable = false)
//    private User user;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @MapsId("idPermission")
//    @JoinColumn(name = "id_permission", nullable = false)
//    private Permission permission;
//
//    public UserPermission() {
//    }
//
//    public UserPermission(User user, Permission permission) {
//        this.user = user;
//        this.permission = permission;
//        this.id = new Id(user.getId(), permission.getId());
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (o == null || getClass() != o.getClass()) return false;
//        UserPermission that = (UserPermission) o;
//        return Objects.equals(id, that.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id);
//    }
//
//    public Id getId() {
//        return id;
//    }
//
//    public void setId(Id id) {
//        this.id = id;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public Permission getPermission() {
//        return permission;
//    }
//
//    public void setPermission(Permission permission) {
//        this.permission = permission;
//    }
//
//    @Embeddable
//    public static class Id implements Serializable {
//        @Serial
//        private static final long serialVersionUID = 1L;
//
//        @Column(name = "id_user", nullable = false)
//        private Long idUser;
//
//        @Column(name = "id_permission", nullable = false)
//        private Long idPermission;
//
//        public Id() {
//        }
//
//        public Id(Long idUser, Long idPermission) {
//            this.idUser = idUser;
//            this.idPermission = idPermission;
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            if (o == null || getClass() != o.getClass()) return false;
//            Id that = (Id) o;
//            return Objects.equals(idUser, that.idUser) && Objects.equals(idPermission, that.idPermission);
//        }
//
//        @Override
//        public int hashCode() {
//            return Objects.hash(idUser, idPermission);
//        }
//
//        public Long getIdUser() {
//            return idUser;
//        }
//
//        public void setIdUser(Long idUser) {
//            this.idUser = idUser;
//        }
//
//        public Long getIdPermission() {
//            return idPermission;
//        }
//
//        public void setIdPermission(Long idPermission) {
//            this.idPermission = idPermission;
//        }
//    }
}