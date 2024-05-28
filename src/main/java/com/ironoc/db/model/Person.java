package com.ironoc.db.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Table;
import javax.validation.constraints.NotNull;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;

@Entity
@Table(name="person")
public class Person {
	
	@Id
	@Column(name="id")
	@GeneratedValue
	private Integer id;
	
	@Column(name="title")
	@NotEmpty(message = "Title is not defined")
	private String title;
	
	@Column(name="first_name")
	@NotEmpty(message = "First Name is not defined")
	private String firstName;
	
	@Column(name="surname")
	@NotEmpty(message = "Surname is not defined")
	private String surname;
	
	@Column(name="age")
	@Min(value=1, message="Age is less than 1")
	@Max(value=90, message="Age is greater than 90")
	@NotNull(message = "Age is not defined")
	private Integer age;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

}
