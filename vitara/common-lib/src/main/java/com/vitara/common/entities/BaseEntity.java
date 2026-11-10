package com.vitara.common.entities;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // need to set the listener for auditing
public class BaseEntity {

    @Column(name = "created_at", updatable = false)
    @CreatedDate // need to set during auditing
    @CreationTimestamp
    private Instant createdAt;

    @CreatedBy  // need to set during auditing
    @Column(name = "created_by", length = 20, updatable = false)
    private String createdBy;
}
