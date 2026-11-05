package com.vitara.common.location.dto;


import com.vitara.common.entities.BaseEntity;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeoCodeDto {
    private Double latitude;
    private Double longitude;
}
