package org.drew.carcenter.data.models;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "appointment")
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
    @GeneratedValue
    private Long id;
    @Column(name="date_time")
    private Timestamp dateTime;
    @Column
    private Status status;
    @OneToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}