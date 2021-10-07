package com.gabriela.Pojisteni.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="email", nullable = false, unique = true, length=45)
    private String email;
    @Column(name="jmeno", length = 45)
    private String name;
    @Column(name="prijmeni", nullable = false, length = 45)
    private String surname;
    @Column(name="heslo", nullable = false, length = 100)
    private String password;
    private boolean enabled;
    // Při smazání uživatele jsou jeho pojištění vymazány
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Insurance> insurances;
    // Uživatel může mít jednu ze tří rolí: user, editor, admin
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    public User() {}

    public User(Integer id) {
        this.id = id;
    }

    public User(String email) {
        this.email = email;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Insurance> getInsurances() {
        return insurances;
    }

    public void setInsurances(List<Insurance> insurances) {
        this.insurances = insurances;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    // Metoda toString pro testování UserRepository testListAll()
    /* @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", jmeno='" + name + '\'' +
                ", prijmeni='" + surname + '\'' +
                ", heslo='" + password + '\'' +
                '}';
    }
     */
}
