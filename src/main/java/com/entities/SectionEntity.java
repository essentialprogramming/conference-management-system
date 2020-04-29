package com.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "section")
public class SectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private EventEntity event;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supervisor_email", unique = true)
    private UserEntity supervisor;

    @OneToMany(mappedBy = "participantsSection", fetch = FetchType.LAZY)
    private List<UserEntity> participants;

    @OneToMany(mappedBy = "section", fetch = FetchType.LAZY)
    private List<PaperEntity> papers;

}
