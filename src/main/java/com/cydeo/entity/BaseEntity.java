package com.cydeo.entity;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
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
    private LocalDateTime insertDateTime;
    private Long insertUserId;
    private LocalDateTime lastUpdateDateTime;
    private Long lastUpdateUserId;

}
