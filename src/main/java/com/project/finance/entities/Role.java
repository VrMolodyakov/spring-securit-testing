package com.project.finance.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name")
    private String role;

    @JsonBackReference
    @ManyToMany(mappedBy = "roles")
    private List<Client> clients;

    public Role(Long id, String role) {
        this.id = id;
        this.role = role;
    }

    @Override
    public String toString() {
        return "Role{" + "id=" + id + ", role='" + role + '\'' + '}';
    }
}
