package com.entities;


import com.model.Role;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user_entity")
@TypeDef(
        name = "pgsql_enum",
        typeClass = PostgreSQLEnumType.class
)
public class UserEntity {

    @Id
    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @OneToOne(mappedBy = "supervisor")
    private SectionEntity section;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "section_id")
    private SectionEntity participantsSection;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "role")
    @Type(type = "pgsql_enum")
    private Role role;

}
