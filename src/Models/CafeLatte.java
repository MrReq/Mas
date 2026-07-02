package Models;

import Enums.CoffeeCountry;
import Enums.TemperatureOfTheService;
import Enums.TypeOfMilk;

import java.util.ArrayList;
import java.util.List;

public class CafeLatte extends Coffee {
    public CafeLatte() {
    }
    //EXTENT SESSION
    /** Extent session contains:
     * <br>to String method</br>
     * <br>{@linkplain List} in implementation as {@linkplain ArrayList} called extent that holds all instances</br>
     * <br>Private Static method "addCafeLatte" which adds instance of {@linkplain CafeLatte} to extent collection</br>
     * <br>Private Static method "removeCafeLatte" which removes instance of {@linkplain CafeLatte} from extent collection</br>
     * <br>Public Static method "showExtent" which displays all instances of {@linkplain CafeLatte} line by line.</br>
     */
    @Override
    public String toString() {
        return "CafeLatte: " + productName + ", id: " + super.toString();
    }
    private static List<CafeLatte> extent = new ArrayList<>();
    private static void addCafeLatte(CafeLatte cafeLatte) {
        extent.add(cafeLatte);
    }
    private static void removeCafeLatte(CafeLatte cafeLatte) {
        extent.remove(cafeLatte);
    }
    public static void showExtent() {
        System.out.println("Extent of the class: " + CafeLatte.class.getName());
        for (CafeLatte cafeLatte : extent) {
            System.out.println(cafeLatte);
        }
    }
    //EXTENT SESSION END
    //FIELDS SESSION START
    /**Complex, Single, Required, Object, Concrete Attribute "typeOfMilk" typed {@linkplain TypeOfMilk}
     */
    TypeOfMilk typeOfMilk;
    /**Simple, Single, Required, Object, Concrete Attribute "milkAmount" typed {@linkplain Double}
     */
    double milkAmount;
    /**Simple, Single, Required, Object, Concrete Attribute "milkFoamLevel" typed {@linkplain Double}
     */
    double milkFoamLevel;
    /**Simple, Single, Required, Object, Concrete Attribute "espressoShotCount" typed {@linkplain Integer}
     */
    int espressoShotCount;
    //FIELDS SESSION END
    //CONSTRUCTORS, GETTERS, SETTERS SESSION START
    /**
     * @param name
     * @param cost
     * @param availability
     * @param description
     * @param temperatureOfService
     */
    public CafeLatte(String name, float cost, boolean availability, String description
            , TemperatureOfTheService temperatureOfService, CoffeeCountry coffeeCountry) {
        super(name, cost, availability, description, temperatureOfService, coffeeCountry);
    }
    //CONSTRUCTORS, GETTERS, SETTERS SESSION END

    public TypeOfMilk getTypeOfMilk() {
        return typeOfMilk;
    }

    public void setTypeOfMilk(TypeOfMilk typeOfMilk) {
        this.typeOfMilk = typeOfMilk;
    }

    public double getMilkAmount() {
        return milkAmount;
    }

    public void setMilkAmount(double milkAmount) {
        this.milkAmount = milkAmount;
    }

    public double getMilkFoamLevel() {
        return milkFoamLevel;
    }

    public void setMilkFoamLevel(double milkFoamLevel) {
        this.milkFoamLevel = milkFoamLevel;
    }

    public int getEspressoShotCount() {
        return espressoShotCount;
    }

    public void setEspressoShotCount(int espressoShotCount) {
        this.espressoShotCount = espressoShotCount;
    }

    //METHODS SESSION START
    @Override
    public String countPowerOfCoffee() {
        return "To kawa z mlekiem ona nie jest mocna ;)";
    }
    //METHODS SESSION END
}
