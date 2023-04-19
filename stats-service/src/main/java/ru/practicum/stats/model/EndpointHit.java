package ru.practicum.stats.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "endpoint_hits", schema = "public")

public class EndpointHit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "endpoint_hit_id")
    private Integer id;

    @Column(nullable = false)
    private String app;

    @Column
    private String uri;

    @Column
    private String ip;

    @Column(nullable = false)
    private LocalDateTime timestamp;
}