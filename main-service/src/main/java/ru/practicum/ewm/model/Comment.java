package ru.practicum.ewm.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Column
    private String text;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "edited_on")
    private LocalDateTime editedOn;

    public Comment(User user, Event event, String text, LocalDateTime createdOn) {
        this.user = user;
        this.event = event;
        this.text = text;
        this.createdOn = createdOn;
    }
}