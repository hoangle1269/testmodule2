package phone;

public abstract class Phone {
    protected int id;
    protected String name;
    protected double price;
    protected int quantity;
    protected String manufacturer;

    public Phone(int id, String name, double price, int quantity, String manufacturer) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.manufacturer = manufacturer;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Price: " + price + ", Quantity: " + quantity + ", Manufacturer: " + manufacturer;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public abstract String toCSV();
}