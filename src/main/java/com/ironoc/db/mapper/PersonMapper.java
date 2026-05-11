package com.ironoc.db.mapper;

import module java.base;

import com.ironoc.db.dto.PersonDto;
import com.ironoc.db.model.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {

    public Person toPerson(PersonDto personDto) {
        return toPerson(null, personDto);
    }

    public Person toPerson(Long id, PersonDto personDto) {
        return Person.builder()
                .id(id)
                .title(personDto.getTitle())
                .firstName(personDto.getFirstName())
                .surname(personDto.getSurname())
                .age(personDto.getAge())
                .build();
    }

    public PersonDto toPersonDto(Person person) {
        return PersonDto.builder()
                .id(person.getId())
                .title(person.getTitle())
                .firstName(person.getFirstName())
                .surname(person.getSurname())
                .age(person.getAge())
                .build();
    }
}
