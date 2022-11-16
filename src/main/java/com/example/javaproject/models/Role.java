package com.example.javaproject.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Role {

    private int roleId;

    private String roleName;

    @Override
    public String toString() {
        return "Role\nid: " + this.roleId + " rolename: " + this.roleName;
    }

}
