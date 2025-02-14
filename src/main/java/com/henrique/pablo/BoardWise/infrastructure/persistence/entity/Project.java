package com.henrique.pablo.BoardWise.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.Where;


@Getter
@Setter
@Entity
@Table(name = "projects")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = "id")
//@Where(clause = "deleted = false")
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

    public void setOwner(User owner) {
        this.owner = owner;
        if(owner != null) {
            owner.getProjects().add(this);
        }
    }

}
