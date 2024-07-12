package phone;

public class AuthenticPhone extends Phone {
    private int warrantyPeriod;
    private String warrantyScope;

    public AuthenticPhone(int id, String name, double price, int quantity, String manufacturer, int warrantyPeriod, String warrantyScope) {
        super(id, name, price, quantity, manufacturer);
        this.warrantyPeriod = warrantyPeriod;
        this.warrantyScope = warrantyScope;
    }

    @Override
    public String toString() {
        return super.toString() + ", Warranty Period: " + warrantyPeriod + " days, Warranty Scope: " + warrantyScope;
    }

    @Override
    public String toCSV() {
        return id + "," + name + "," + price + "," + quantity + "," + manufacturer + "," + warrantyPeriod + "," + warrantyScope;
    }
}
