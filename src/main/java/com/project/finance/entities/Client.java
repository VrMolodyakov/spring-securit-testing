package com.project.finance.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "clients")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long id;

    @Column(name = "client_login")
    @Length(min = 5, message = "*Your user name must have at least 5 characters")
    @NotEmpty(message = "*Please provide a user name")
    private String login;

    @Column(name = "client_email")
    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    private String email;

    @Column(name = "password")
    @Length(min = 8, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
    private String password;

    @Column(name = "date_added")
    //@Temporal(TemporalType.DATE)
    private LocalDateTime creationDate;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "client_role",
               joinColumns = @JoinColumn(name = "client_id"),
               inverseJoinColumns = @JoinColumn(name = "role_id")
               )
    private Set<Role> roles;

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id) && Objects.equals(login, client.login) && Objects
                .equals(email, client.email) && Objects.equals(creationDate, client.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, email, creationDate);
    }
}
