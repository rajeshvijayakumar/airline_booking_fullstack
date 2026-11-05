package com.vitara.location.dto;

import com.vitara.common.location.entities.Address;
import com.vitara.common.location.entities.GeoCode;
import com.vitara.location.entities.City;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirportDto {
    private Long id;
    private String iataCode;
    private String name;
    private String timeZoneId;
    private Address address;
    private City city;
    private GeoCode geoCode;
}
