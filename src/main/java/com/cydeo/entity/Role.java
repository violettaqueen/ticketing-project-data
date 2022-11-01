package com.cydeo.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter //5
@Setter //6
@NoArgsConstructor
@AllArgsConstructor
@Entity //
@Table(name = "roles")
public class Role extends BaseEntity{
    
    private String description;

}
