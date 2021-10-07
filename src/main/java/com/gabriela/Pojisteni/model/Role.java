package com.gabriela.Pojisteni.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="roles")
public class Role {
    @Id
    @Column(name="role_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer role_id;
    @Column(nullable = false)
    private String name;
    @OneToMany(mappedBy = "role")
    private List<User> users;

    public Role() {}

    public Role(Integer role_id, String name) {
        this.role_id = role_id;
        this.name = name;
    }

    public Role(Integer role_id) {
        this.role_id = role_id;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
