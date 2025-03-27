package com.ironoc.db.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="employer")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employer {

    @Id
    @Column(name="employer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employer_id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Person person;

    @Column(name="title")
    private String title;

    @Column(name="employer_name")
    private String employerName;

    @Column(name="start_year")
    private Integer startYear;
}
