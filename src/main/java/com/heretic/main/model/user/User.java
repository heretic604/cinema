package com.heretic.main.model.user;

import com.heretic.main.util.Values;

import java.time.LocalDate;
import java.util.Objects;

public class User {

    private Long id;
    private final String username;
    private String password;
    private String email;
    private Status status;
    private Role role;
    private final LocalDate birthday;

    @Override
    public String toString() {
        return String.format(Values.SAMPLE_USER, username, id, role.getTitle(), status.getTitle(), email, birthday);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public User(String username, String password, String email, LocalDate birthday) {
        this.username = username;
        this.password = password;
        this.email = email;
        status = Status.ACTIVE;
        role = Role.VISITOR;
        this.birthday = birthday;
    }

    public User(Long id, String username, String password, String email, Status status, Role role, LocalDate birthday) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.status = status;
        this.role = role;
        this.birthday = birthday;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

}
