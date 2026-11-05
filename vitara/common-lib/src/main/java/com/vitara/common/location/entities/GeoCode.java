package com.vitara.common.location.entities;


import com.vitara.common.entities.BaseEntity;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeoCode extends BaseEntity {
    private Double latitude;
    private Double longitude;
}
