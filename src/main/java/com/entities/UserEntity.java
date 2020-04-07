package com.entities;


import com.model.Qualifier;
import com.model.Role;
import com.vladmihalcea.hibernate.type.array.EnumArrayType;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

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

    @ManyToMany(mappedBy = "authors")
    private List<ProposalEntity> proposalEntitiesAuthors;

    @ManyToMany(mappedBy = "reviewers")
    private List<ProposalEntity> proposalEntitiesReviewers;

    @ManyToMany(mappedBy = "programCommittee")
    private List<EventEntity> eventsPC;

    @ManyToMany(mappedBy = "participants")
    private List<EventEntity> eventsParticipants;

    @ManyToMany(mappedBy = "speakers")
    private List<EventEntity> eventsSpeakers;

    @OneToOne(mappedBy = "supervisor")
    private SectionEntity section;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    private SectionEntity participantsSection;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "role")
    @Type( type = "pgsql_enum" )
    private Role role;

}
