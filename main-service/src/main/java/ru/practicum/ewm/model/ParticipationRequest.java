package ru.practicum.ewm.model;

import lombok.*;
import org.hibernate.Hibernate;
import ru.practicum.ewm.enumeration.RequestStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events_users")
public class ParticipationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "status")
    private RequestStatus status;

    public ParticipationRequest(User requester, Event event, LocalDateTime created) {
        this.requester = requester;
        this.event = event;
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        ParticipationRequest participationRequest = (ParticipationRequest) o;
        return getId() != null && Objects.equals(getId(), participationRequest.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}