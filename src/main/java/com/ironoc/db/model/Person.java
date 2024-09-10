package com.ironoc.db.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.validation.constraints.*;

@Entity
@Table(name="person")
public class Person {
	
	@Id
	@Column(name="id")
	@GeneratedValue
	private Integer id;
	
	@Column(name="title")
	@NotEmpty(message = "Title is not defined.")
	@Size(min = 2, max = 5, message = "Title should be between 2-5 characters.")
	private String title;
	
	@Column(name="first_name")
	@NotEmpty(message = "First Name is not defined.")
	@Size(min = 3, max = 30, message = "First Name should be between 3-30 characters.")
	private String firstName;
	
	@Column(name="surname")
	@Size(min = 3, max = 30, message = "Surname should be between 3-30 characters.")
	private String surname;
	
	@Column(name="age")
	@Min(value=1, message="Age is less than 1.")
	@Max(value=90, message="Age is greater than 90.")
	@NotNull(message = "Age is not defined.")
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

	public static class PersonBuilder {

		private Integer id;
		private String title;
		private String firstName;
		private String surname;
		private Integer age;

		public PersonBuilder withId(Integer id) {
			this.id = id;
			return this;
		}

		public PersonBuilder withTitle(String title) {
			this.title = title;
			return this;
		}

		public PersonBuilder withFirstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public PersonBuilder withSurname(String surname) {
			this.surname = surname;
			return this;
		}

		public PersonBuilder withAge(int age) {
			this.age = age;
			return this;
		}

		public Person build() {
			Person person = new Person();
			person.setId(this.id);
			person.setTitle(this.title);
			person.setFirstName(this.firstName);
			person.setSurname(this.surname);
			person.setAge(this.age);
			return person;
		}
	}
}
