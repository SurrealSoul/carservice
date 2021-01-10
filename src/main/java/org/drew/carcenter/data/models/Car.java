package org.drew.carcenter.data.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "car", schema = "public")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String make;
    @Column
    private String model;
    @Column
    private String year;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
