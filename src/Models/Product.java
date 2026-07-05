package Models;
import Enums.TemperatureOfTheService;
import Interfaces.Preparable;
import SecondaryClasses.ObjectPlus;
import java.util.ArrayList;
import java.util.List;
public abstract class Product extends ObjectPlus implements Preparable {
    private static final long serialVersionUID = 1L;
    @Override
    public String toString() {
        return "Product: " + productName;
    }
    static int staticProductID = 1;
    int productID;
    String productName;
    float productCost;
    boolean productAvailability;
    List<String> productIngredients;
    String productDescription;
    TemperatureOfTheService temperatureOfTheService;
    static List<Product> products;

    @SuppressWarnings("unchecked")
    List<Product> productsExtent =
            (List<Product>)(List<?>)ObjectPlus.getExtent(Product.class);
    public static List<Product> getProductExtent() {
        List<Product> result = new ArrayList<>();
        result.addAll((List<Product>)(List<?>)ObjectPlus.getExtent(Americano.class));
        result.addAll((List<Product>)(List<?>)ObjectPlus.getExtent(CafeLatte.class));
        result.addAll((List<Product>)(List<?>)ObjectPlus.getExtent(Espresso.class));
        return result;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public float getProductCost() {
        return productCost;
    }
    public void setProductCost(float productCost) {
        this.productCost = productCost;
    }
    public boolean isProductAvailability() {
        return productAvailability;
    }
    public void setProductAvailability(boolean productAvailability) {
        this.productAvailability = productAvailability;
    }
    public List<String> getProductIngredients() {
        return productIngredients;
    }
    public void setProductIngredients(List<String> productIngredients) {
        this.productIngredients = productIngredients;
    }
    public String getProductDescription() {
        return productDescription;
    }
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
    public TemperatureOfTheService getTemperatureOfTheService() {
        return temperatureOfTheService;
    }
    public void setTemperatureOfTheService(TemperatureOfTheService temperatureOfTheService) {
        this.temperatureOfTheService = temperatureOfTheService;
    }
    public static List<Product> getProducts() {
        return products;
    }
    public int getProductID() {
        return productID;
    }
    public static void setProducts(List<Product> products) {
        Product.products = products;
    }
    public Product(String name, float cost, boolean availability, String description, TemperatureOfTheService
            temperatureOfService){
        this.productID = staticProductID++;
        this.productName = name;
        this.productCost = cost;
        this.productAvailability = availability;
        this.productDescription = description;
        this.temperatureOfTheService = temperatureOfService;
    }
    public Product(int poductID, String name, float cost, boolean availability, String description, TemperatureOfTheService temperatureOfService){
        this.productID = poductID;
        this.productName = name;
        this.productCost = cost;
        this.productAvailability = availability;
        this.productDescription = description;
        this.temperatureOfTheService = temperatureOfService;
    }
    public Product(){}
    public static void createListOfAllProducts () {
        System.out.println("that is the list of the productList");
        products.forEach(System.out::println);
    }
    public static List<Product> getAvailableProducts() {
        return getProductExtent()
                .stream()
                .filter(Product::isProductAvailability)
                .toList();
    }
    public double countOrderValue() {
        return products.stream()
                .mapToDouble(Product::getProductCost)
                .sum();
    }
    public void changePrice(float newPrice){
        if(newPrice <= 0){throw new IllegalArgumentException("Price must be greater than zero.");
        }
        productCost = newPrice;
    }
    public static Product findById(int id) {
        for (Product product : getProductExtent()) {
            if (product.getProductID() == id)
                return product;
        }
        return null;
    }
    public static void productRebuildCounter() {
        int maxId = 0;
        for (Product product : getProductExtent()) {
            if (product.getProductID() > maxId)
                maxId = product.getProductID();
        }
        staticProductID = maxId + 1;
    }
}
