package com.example.javaproject.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "userroles")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int roleId;

    @Column(name = "name")
    private String roleName;

    @OneToMany(mappedBy = "role")
    private Set<User> userModels;

    @Override
    public String toString() {
        return "Role\nid: " + this.roleId + " rolename: " + this.roleName;
    }

}
