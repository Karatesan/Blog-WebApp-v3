package com.karatesan.WebAppApi.model.security.role;

import com.karatesan.WebAppApi.model.security.BlogUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @ManyToMany(mappedBy = "roles")
    private Collection<BlogUser> users;
    @ManyToMany
    //tak sie tworzy join table, robi sie to w many to many relations
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"))
    //(fetch = FetchType.EAGER) mappedBy ma byc takie jak pole w drugim obiekjcie z relacji
    //mappedBy jest tu gdzie jest lista i mowi co ownuje ta relacje, czyli gdzie jest foreign key
    private Collection<Privilege> privileges;


    public Role(String name) {
        this.name = name;
    }

    public Role(String name, Collection<Privilege> privileges) {
        this.name = name;
        this.privileges = privileges;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<BlogUser> getUsers() {
        return users;
    }

    public void setUsers(Collection<BlogUser> users) {
        this.users = users;
    }

    public Collection<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Collection<Privilege> privileges) {
        this.privileges = privileges;
    }
}


