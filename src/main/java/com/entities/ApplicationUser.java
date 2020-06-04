package com.entities;

import com.model.Role;
import com.vladmihalcea.hibernate.type.array.EnumArrayType;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(
        typeClass = EnumArrayType.class,
        defaultForType = List.class,
        parameters = {
                @org.hibernate.annotations.Parameter(name = EnumArrayType.SQL_ARRAY_TYPE, value = "role")
        }
)
@Entity(name = "profile")
public class ApplicationUser {

    @Id
    @Email
    @Column(name = "username", nullable = false, unique = true)
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<RoleEntity> roles = new ArrayList<>();;




}
