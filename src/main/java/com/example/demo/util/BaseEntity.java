package com.example.demo.util;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

import javax.persistence.*;
import java.time.LocalDateTime;

// Study if have time
// https://stackoverflow.com/questions/25486583/how-to-use-orderby-with-findall-in-spring-data

@Data
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {
    @Transient
    public static final Sort SORT_BY_CREATED_AT_DESC = Sort.by(Sort.Direction.DESC, "id");
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
