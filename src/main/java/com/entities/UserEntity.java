package com.entities;


import com.model.Role;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Objects;


@Builder
@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity)) return false;
        UserEntity entity = (UserEntity) o;
        return Objects.equals(section, entity.section) &&
                Objects.equals(participantsSection, entity.participantsSection) &&
                role == entity.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(section, participantsSection, role);
    }
}
