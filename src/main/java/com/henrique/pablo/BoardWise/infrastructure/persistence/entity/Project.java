package com.henrique.pablo.BoardWise.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.SoftDelete;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "projects")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = "id")
@SQLRestriction("deleted = false")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;
    private String description;

    @SoftDelete
    private boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "project_participants",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> participants = new HashSet<>();

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BoardList> boardLists = new HashSet<>();

    public void addParticipant(User user){
        this.participants.add(user);
        user.getParticipatingProjects().add(this);
    }

    public void removeParticipant(User user){
        this.participants.remove(user);
        user.getParticipatingProjects().remove(this);
    }
}
