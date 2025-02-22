package com.arthur.taslmanager.entities;

import com.arthur.taslmanager.enums.Priority;
import com.arthur.taslmanager.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Data
@Table(name = "tasks")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "title")
    String title;

    @Column(name = "description")
    String description;

    @ManyToOne
    @JoinColumn(name = "author_id")
    User author;

    @ManyToOne
    @JoinColumn(name = "performer_id")
    User performer;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    Status status;

    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    Priority priority;

    @OneToMany
    List<Comment> commentList;
}
