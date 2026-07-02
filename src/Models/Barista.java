package Models;

import Enums.CoffeeCountry;
import Enums.Sex;
import SecondaryClasses.ObjectPlus;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class Barista extends Employee {

    //====================================================
    // ATTRIBUTES
    //====================================================

    /**
     * Optional attribute.
     * Favourite coffee bean origin.
     */
    private CoffeeCountry favouriteCoffeeCountry;

    //====================================================
    // CONSTRUCTORS
    //====================================================

    public Barista() {
        super();
    }

    public Barista(String name,
                   String surname,
                   LocalDate dateOfBirth,
                   Sex sex,
                   float salary,
                   CoffeeCountry favouriteCoffeeCountry) {

        super(name, surname, dateOfBirth, sex, salary);

        this.favouriteCoffeeCountry = favouriteCoffeeCountry;

    }

    //====================================================
    // EXTENT
    //====================================================

    @SuppressWarnings("unchecked")
    public static List<Barista> getBaristaExtent() {
        return (List<Barista>) (List<?>) ObjectPlus.getExtent(Barista.class);

    }

    //====================================================
    // GETTERS
    //====================================================

    public CoffeeCountry getFavouriteCoffeeCountry() {
        return favouriteCoffeeCountry;
    }

    //====================================================
    // SETTERS
    //====================================================

    public void setFavouriteCoffeeCountry(CoffeeCountry favouriteCoffeeCountry) {
        this.favouriteCoffeeCountry = favouriteCoffeeCountry;
    }

    //====================================================
    // BUSINESS METHODS
    //====================================================

    @Override
    public String getPrivileges() {
        return "BARISTA";
    }

    public void makeCoffee() {
        System.out.println(
                getPersonName() + " is preparing coffee."
        );

    }

    //====================================================
    // SERIALIZATION
    //====================================================

    @Override
    protected void write(DataOutputStream stream)
            throws IOException {

        super.write(stream);

        stream.writeBoolean(favouriteCoffeeCountry != null);

        if (favouriteCoffeeCountry != null) {

            stream.writeUTF(favouriteCoffeeCountry.name());

        }

    }

    @Override
    protected void read(DataInputStream stream)
            throws IOException {

        super.read(stream);

        boolean hasCountry = stream.readBoolean();

        if (hasCountry) {

            favouriteCoffeeCountry =
                    CoffeeCountry.valueOf(stream.readUTF());

        } else {

            favouriteCoffeeCountry = null;

        }

    }

    //====================================================
    // OBJECT METHODS
    //====================================================

    @Override
    public String toString() {

        return String.format(

                "Barista{id=%d, name='%s %s', salary=%.2f, favouriteCoffeeCountry=%s}",

                employeeID,

                personName,

                peronSurname,

                employeeSalary,

                favouriteCoffeeCountry

        );

    }

    public void acceptOrder(Order order){

        order.acceptOrder();

    }

    public void startPreparing(Order order){

        order.startPreparation();

    }

    public void prepareDrink(Product product){
        product.prepare();
    }

    public void markOrderReady(Order order){
        order.markAsReady();
    }

}