package com.cydeo.entity;
import com.cydeo.enums.Gender;
import com.cydeo.enums.Status;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "projects")
@Where(clause = "is_deleted = false")
public class Project extends BaseEntity{

    private String projectCode;

    private String projectName;

    private LocalDate startDate;

    private LocalDate endDate;
    @Enumerated(value = EnumType.STRING)
    private Status projectStatus;

    private String projectDetail;
    @ManyToOne
    private User assignedManager;

}
