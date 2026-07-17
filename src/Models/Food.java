package Models;
import java.util.List;
import Enums.Doneness;
import Enums.TemperatureOfTheService;
import Enums.TypeOfMeal;
import SecondaryClasses.ObjectPlus;
public class Food extends Product {
    private static final long serialVersionUID = 1L;
    @Override
    public String toString() {
        return "Food: " + productName + " " + super.toString() +" "+ weight + " " + typeOfMeal + " " + doneness;
    }
    @SuppressWarnings("unchecked")
    public static List<Food> getFoodExtent() {
        return (List<Food>) (List<?>) ObjectPlus.getExtent(Food.class);
    }
    double weight;
    TypeOfMeal typeOfMeal;
    Doneness doneness;
    public Food(String name, float cost, boolean availability, String description, TemperatureOfTheService temperatureOfService) {
        super(name, cost, availability, description, temperatureOfService);
    }
    public Food(String name, float cost, boolean availability, String description, TemperatureOfTheService temperatureOfService, double weight, TypeOfMeal typeOfMeal, Doneness doneness) {
        super(name, cost, availability, description, temperatureOfService);
        this.weight = weight;
        this.typeOfMeal = typeOfMeal;
        this.doneness = doneness;
    }
    @Override
    public void prepare() {
    }
    @Override
    public int getPreparationTime() {
        return 0;
    }
}
