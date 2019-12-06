package com.OfficeManager.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "OFFICE_ASSIGNMENT")
@AssociationOverrides({
        @AssociationOverride(name = "pk.person", joinColumns = @JoinColumn(name = "PERSON_ID")),
        @AssociationOverride(name = "pk.office", joinColumns = @JoinColumn(name = "OFFICE_ID"))
})
public class OfficeAssignment {

    private Integer id;
    private LocalDate startDate, endDate;

    public OfficeAssignment(LocalDate startDate, LocalDate endDate) {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "OFFICE_ASSIGNMENT_ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "START_DATE", nullable = false)
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Column(name = "END_DATE", nullable = false)
    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
