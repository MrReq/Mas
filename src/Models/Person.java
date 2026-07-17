package Models;

import Enums.Sex;
import SecondaryClasses.ObjectPlus;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public abstract class Person extends ObjectPlus {

    private static final long serialVersionUID = 1L;

    // ================================= COUNTER =================================

    private static int counter = 1;

    public static LocalDate now = LocalDate.now();

    // ================================= ATTRIBUTES =================================

    protected String personName;
    protected String peronSurname;
    protected LocalDate personDateOfBirth;
    protected Sex personSex;
    protected int age;

    // ================================= CONSTRUCTORS =================================

    public Person() {
        super();
    }

    public Person(String name, String surname) {
        super();
        this.personName = name;
        this.peronSurname = surname;
    }

    public Person(String name, String surname, LocalDate dateOfBirth, Sex sex) {
        super();
        this.personName = name;
        this.peronSurname = surname;
        this.personDateOfBirth = dateOfBirth;
        this.personSex = sex;
        counter++;
    }

    // ================================= EXTENT =================================

    @SuppressWarnings("unchecked")
    public static List<Person> getPersonExtent() {
        return (List<Person>) (List<?>) ObjectPlus.getExtent(Person.class);
    }

    // ================================= GETTERS =================================
    public static int getCounter() {
        return counter;
    }
    public String getPersonName() {
        return personName;
    }
    public String getPeronSurname() {
        return peronSurname;
    }
    public LocalDate getPersonDateOfBirth() {
        return personDateOfBirth;
    }
    public Sex getPersonSex() {
        return personSex;
    }
    public int getAge() {
        return age;
    }
    public static LocalDate getNow() {
        return now;
    }
    public abstract String getPrivileges();
    @Override
    public String toString() {
        return personName + " " + peronSurname;
    }
}