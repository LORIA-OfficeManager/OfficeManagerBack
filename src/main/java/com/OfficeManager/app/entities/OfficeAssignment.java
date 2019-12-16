package com.OfficeManager.app.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "OFFICE_ASSIGNMENT")
@AssociationOverrides({
        @AssociationOverride(name = "person", joinColumns = @JoinColumn(name = "PERSON_ID")),
        @AssociationOverride(name = "office", joinColumns = @JoinColumn(name = "OFFICE_ID"))
})
public class OfficeAssignment implements Serializable {

    private Integer id;
    private LocalDate startDate, endDate;

    private Person person;
    private Office office;

    public OfficeAssignment(){

    }

    public OfficeAssignment(LocalDate startDate, LocalDate endDate, Person person, Office office) {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
        this.person = person;
        this.office = office;
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

    @ManyToOne
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @ManyToOne
    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }
}
