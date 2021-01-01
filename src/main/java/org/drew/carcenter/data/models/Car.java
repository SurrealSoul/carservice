package org.drew.carcenter.data.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;


/**
 * The data model for the car object.
 * Note: Right now there would be a degree of duplication in this table, if the same user bought the same car and sent
 * it to be repaired, duplicated information would be stored in this table. We could add license plate or some other
 * info to keep it distinct, but that information could change as well. I am ok with that duplication for now, but
 * we could create another controller for updating the car information to alleviate the issue
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "car")
public class Car
{
    @Id
    @GeneratedValue
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
