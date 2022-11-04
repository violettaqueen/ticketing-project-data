package com.cydeo.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter //4
@Setter //5
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass  //1
public class BaseEntity {

    @Id  //2
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //3
    private Long id;

    private Boolean isDeleted = false;

    private LocalDateTime insertDateTime;
    private Long insertUserId;
    private LocalDateTime lastUpdateDateTime;
    private Long lastUpdateUserId;

    @PrePersist
    private void onPrePersist(){
        this.insertDateTime = LocalDateTime.now();
        this.lastUpdateDateTime = LocalDateTime.now();
        this.insertUserId = 1L;
        this.lastUpdateUserId = 1L;
    }
    @PreUpdate
    private void onPreUpdate(){
        this.lastUpdateDateTime = LocalDateTime.now();
        this.lastUpdateUserId= 1L;

    }

}
