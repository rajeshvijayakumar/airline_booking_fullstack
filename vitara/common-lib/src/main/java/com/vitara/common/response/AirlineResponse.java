package com.vitara.common.response;

import com.vitara.common.dto.UserDto;
import com.vitara.common.embeddable.Support;
import com.vitara.common.enums.AirlineStatus;
import com.vitara.common.location.response.CityResponse;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirlineResponse {

    private Long id;

    private String iataCode;
    private String icaoCode;

    private String name;
    private String alias;


    private String logoUrl;
    private String website;

    private AirlineStatus status;
    private String alliance;

    private Instant createdAt;
    private Instant updatedAt;

    private Long ownerId;
    private UserDto owner;
    private Long updatedById;

    private CityResponse headquartersCity;
    private Support support;
}
