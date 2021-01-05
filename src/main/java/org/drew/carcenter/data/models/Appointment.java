package org.drew.carcenter.data.models;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "appointment", schema = "public")
public class Appointment
{
    @AllArgsConstructor
    @Getter
    public enum Status {
        pending,
        approved,
        in_progress,
        completed;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="date_time")
    private Timestamp dateTime;
    @Enumerated(EnumType.STRING)
    @Column
    private Status status;
    @Column
    private String service;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @OneToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;
    @Column
    private Double price;
}