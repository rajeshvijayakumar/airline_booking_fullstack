package com.vitara.location.entities;

import com.vitara.common.location.entities.Address;
import com.vitara.common.location.entities.GeoCode;
import com.vitara.common.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "airports", indexes = {
        @Index(name = "idx_airport_iata", columnList = "iataCode"),
        @Index(name = "idx_airport_city_id", columnList = "city_id")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Airport extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    private Long id;

    @Column(nullable = false, length = 3, unique = true)
    @ToString.Include
    private String iataCode;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(name = "time_zone_id", length = 50)
    private String timeZoneId;

    @Embedded
    private Address address;

    // Same bounded context — direct JPA relationship
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @Embedded
    private GeoCode geoCode;
}