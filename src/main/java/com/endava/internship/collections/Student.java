package com.endava.internship.collections;

import java.time.LocalDate;

/**
 * The class that defines the element that will be contained by your collection
 */
public class Student implements Comparable//TODO consider implementing any interfaces necessary for your collection
{
    private String name;
    private LocalDate dateOfBirth;
    private String details;

    public Student(){
        name = null;
        dateOfBirth = null;
        details = null;
    }
    public Student(String name, LocalDate dateOfBirth, String details) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public int compareTo(Object o) {
        Student student = (Student) o;
        if (this.name.compareTo(student.name) == 0 &&
                this.dateOfBirth.compareTo(student.dateOfBirth) == 0 &&
                this.details.compareTo(student.details) == 0)
            return 0;
        else if ((this.name.compareTo(student.name) > 0) ||
                (this.name.compareTo(student.name) == 0 && this.dateOfBirth.compareTo(student.dateOfBirth) > 0) ||
                (this.name.compareTo(student.name) == 0 && this.dateOfBirth.compareTo(student.dateOfBirth) == 0 &&
                        this.details.compareTo(student.details) > 0))
            return 1;
        else
            return -1;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", details='" + details + '\'' +
                '}';
    }

    /*
    TODO consider overriding any methods for this object to function properly within a collection:
        1. A student is considered unique by a combination of their name and dateOfBirth
        2. Student names are sorted alphabetically, if two students have the same name, then the older one is
        placed before the younger student in an ordered student list.
    */
}
