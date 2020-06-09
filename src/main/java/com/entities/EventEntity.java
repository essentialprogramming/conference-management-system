package com.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DiscriminatorFormula;
import org.hibernate.annotations.DiscriminatorOptions;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "event")
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_event",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "email"))
    @Where(clause = "type= 'pcmember'")
    private List<CommitteeMemberEntity> programCommittee;

    @ManyToMany(fetch = FetchType.LAZY )
    @JoinTable(
            name = "user_event",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "email"))
    @Where(clause = "type= 'participant'")
    private List<UserEntity> participants;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_event",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "email"))
    @Where(clause = "type= 'author'")
    private List<AuthorEntity> speakers;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "program_id")
    private ProgramEntity program;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private LocationEntity location;

    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<SectionEntity> sections;
}
