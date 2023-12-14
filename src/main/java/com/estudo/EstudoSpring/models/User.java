package com.estudo.EstudoSpring.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = User.TABLE_NAME)
public class User {
    public interface CreateUser {
    
    }

    public interface UpdateUser {
    
    }

    public static final String TABLE_NAME = "user";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name="name", length = 100, nullable = false)
    @NotNull(groups = CreateUser.class)
    @NotEmpty(groups = CreateUser.class)
    @Size(min = 3, max = 100)
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name="password", length = 60, nullable = false)
    @NotEmpty(groups = {CreateUser.class, UpdateUser.class})
    @NotNull(groups = {CreateUser.class, UpdateUser.class})
    @Size(min = 6, max = 60)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Task> tasks =  new ArrayList<Task>();
    
    public User() {
    }
    
    public User(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
            return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof User){
            User user = (User) obj;
            return this.id == user.getId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
