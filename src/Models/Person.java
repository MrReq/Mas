package Models;

import Enums.Sex;
import SecondaryClasses.ObjectPlus;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Person extends ObjectPlus {
    public Person(String name, String surname) {
        this.personName = name;
        this.peronSurname = surname;
    }

    //EXTENT SESSION
    /** Extent session contains:
     * <br>to String method</br>
     * <br>{@linkplain List} in implementation as {@linkplain ArrayList} called extent that holds all instances</br>
     * <br>Private Static method "addPerson" which adds instance of {@linkplain Person} to extent collection</br>
     * <br>Private Static method "removePerson" which removes instance of {@linkplain Person} from extent collection</br>
     * <br>Public Static method "showExtent" which displays all instances of {@linkplain Person} line by line.</br>
     */
    @Override
    public String toString() {
        return "Person: " + personName + ", id: " + super.toString();
    }
    private static List<Person> extent = new ArrayList<>();
    private static void addPerson(Person person) {
        extent.add(person);
    }
    private static void removePerson(Person person) {
        extent.remove(person);
    }
    public static void showExtent() {
        System.out.println("Extent of the class: " + Person.class.getName());
        for (Person person : extent) {
            System.out.println(person);
        }
    }
    //EXTENT SESSION END
    // SERIALIZATION (WRITE)
    protected void write(DataOutputStream stream) throws IOException {
        stream.writeInt(counter);
        stream.writeUTF(personName);
        stream.writeUTF(peronSurname);
        stream.writeLong(personDateOfBirth.toEpochDay());
        stream.writeUTF(personSex != null ? personSex.toString() : "");
    }
    // SERIALIZATION (READ)
    protected void read(DataInputStream stream) throws IOException {
        counter = stream.readInt();
        personName = stream.readUTF();
        peronSurname = stream.readUTF();
        long epochDay = stream.readLong();
        personDateOfBirth = LocalDate.ofEpochDay(epochDay);
        String sexStr = stream.readUTF();
        personSex = sexStr.isEmpty() ? null : Sex.valueOf(sexStr);
    }
    //FIELDS SESSION START
    /**Simple, Single, Required, Class, Concrete Attribute "favouriteCoffee" typed {@linkplain Integer}
     */
    private static int counter = 1;
    /**Simple, Single, Required, Object, Concrete Attribute "personName" typed {@linkplain String}
     */
    String personName;
    /**Simple, Single, Required, Object, Concrete Attribute "peronSurname" typed {@linkplain String}
     */
    String peronSurname;
    /**Complex, Single, Required, Object, Concrete Attribute "personDateOfBirth" typed {@linkplain LocalDate}
     */
    LocalDate personDateOfBirth;
    /**Simple, Single, Required, Object, Concrete Attribute "personSex" typed {@linkplain Sex}
     */
    Sex personSex;
    /**Complex, Single, Required, Class, Derived Attribute "now" typed {@linkplain LocalDate}
     */
    public static LocalDate now = LocalDate.now();
    //derived attribute age which is based on present time and date of Birth of this current object
    int age;
    //CONSTRUCTORS, GETTERS, SETTERS SESSION START
    public Person(String name, String surname, LocalDate dateOfBirth, Sex sex) {
        this.personName = name;
        this.peronSurname = surname;
        this.personDateOfBirth = dateOfBirth;
        this.personSex = sex;
        counter++;
//        age = Period.between(personDateOfBirth, LocalDate.now()).getYears();
        addPerson(this);
    }
    public Person() {
    }

    public static List<Person> getExtent() {
        return extent;
    }

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

    public static LocalDate getNow() {
        return now;
    }

    public int getAge() {
        return age;
    }

    //CONSTRUCTORS, GETTERS, SETTERS SESSION END
    //METHODS SESSION START
    public abstract String getPrivileges();
    //METHODS SESSION END
}
