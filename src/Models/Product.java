package Models;

import Enums.TemperatureOfTheService;
import Interfaces.Preparable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Product implements Preparable {
    //EXTENT SESSION
    /**
     * Extent session contains:
     * <br>to String method</br>
     * <br>{@linkplain List} in implementation as {@linkplain ArrayList} called extent that holds all instances</br>
     * <br>Private Static method "addProduct" which adds instance of {@linkplain Product} to extent collection</br>
     * <br>Private Static method "removeProduct" which removes instance of {@linkplain Product} from extent collection</br>
     * <br>Public Static method "showExtent" which displays all instances of {@linkplain Product} line by line.</br>
     */
    @Override
    public String toString() {
        return "Product: " + productName;
    }
    private static List<Product> extent = new ArrayList<>();
    private static void addProduct (Product product){
        extent.add(product);
    }
    private static void removeProduct (Product product){
        extent.remove(product);
    }
    public static void showExtent () {
        System.out.println("Extent of the class: " + Product.class.getName());
        for (Product product : extent) {
            System.out.println(product);
        }
    }
    protected void write(DataOutputStream stream) throws IOException {
        stream.writeUTF(productName);
        stream.writeFloat(productCost);
        stream.writeBoolean(productAvailability);
        stream.writeInt(productIngredients.size());
        for (String experience : productIngredients) {
            stream.writeUTF(experience);
        }
        stream.writeUTF(productDescription);
        stream.writeUTF(temperatureOfTheService != null ? temperatureOfTheService.toString() : "");
    }
    protected void read(DataInputStream stream) throws IOException {
        productName = stream.readUTF();
        productCost = stream.readFloat();
        productAvailability = stream.readBoolean();
        int size = stream.readInt();
        productIngredients = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            String productIngredientsString = stream.readUTF();
            productIngredients.add(productIngredientsString);
        }
        productDescription = stream.readUTF();
    }
    public static void writeExtent(DataOutputStream stream) throws IOException {
        // Number of objects
        stream.writeInt(extent.size());
        for (Product product : extent) {
            product.write(stream);
        }
    }
    public static void readExtent(DataInputStream stream) throws IOException {
        Product product = null;
        // Get the number of written objects
        int objectCount = stream.readInt();
        // remove the current extent
        extent.clear();
        for (int i = 0; i < objectCount; i++) {
            product = new Product() {
                @Override
                public void prepare() {
                }
                @Override
                public int getPreparationTime() {
                    return 0;
                }
            };
            product.read(stream);
        }
    }
    //EXTENT SESSION END
    //FIELDS SESSION
    static int staticProductID = 1;
    /**Simple, Single, Required, Object, Concrete Attribute "productName" typed {@linkplain String}
     */
    int productID;
    String productName;
    /**Simple, Single, Required, Object, Concrete Attribute "productCost" typed {@linkplain Float}
     */
    float productCost;
    /**Simple, Single, Required, Object, Concrete Attribute "productAvailability" typed {@linkplain Boolean}
     */
    boolean productAvailability;
    /**Complex, Repeatable, Optional, Object, Concrete Attribute "productIngredients" typed {@linkplain List}
     */
    List<String> productIngredients;
    /**Simple, Single, Required, Object, Concrete Attribute "productDescription" typed {@linkplain String}
     */
    String productDescription;
    /**Simple, Single, Required, Object, Concrete Attribute "temperatureOfService" typed {@linkplain TemperatureOfTheService}
     */
    TemperatureOfTheService temperatureOfTheService;
    /**Complex, Single, Optional, Object, Concrete Attribute "products" typed {@linkplain List}
     */
    static List<Product> products;
    //FIELDS SESSION END
    //CONSTRUCTORS, GETTERS, SETTERS SESSION START

    public static List<Product> getExtent() {
        return extent;
    }

    public static void setExtent(List<Product> extent) {
        Product.extent = extent;
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
        addProduct(this);
    }
    public Product(int poductID, String name, float cost, boolean availability, String description, TemperatureOfTheService
            temperatureOfService){
        this.productID = poductID;
        this.productName = name;
        this.productCost = cost;
        this.productAvailability = availability;
        this.productDescription = description;
        this.temperatureOfTheService = temperatureOfService;
        addProduct(this);
    }
    public Product() {
        addProduct(this);
    }
    //CONSTRUCTORS, GETTERS, SETTERS SESSION END
    //METHODS SESSION START
    public static void createListOfAllProducts () {
        System.out.println("that is the list of the productList");
        products.forEach(System.out::println);
    }
    public static List<Product> getAvailableProducts() {

        return getExtent()
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
        if(newPrice <= 0){
            throw new IllegalArgumentException(
                    "Price must be greater than zero."
            );
        }
        productCost = newPrice;
    }

    //METHODS SESSION END
}
