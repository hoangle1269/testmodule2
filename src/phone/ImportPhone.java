package phone;

public class ImportPhone extends Phone {
    private String importCountry;
    private String usageStatus;

    public ImportPhone(int id, String name, double price, int quantity, String manufacturer, String importCountry, String usageStatus) {
        super(id, name, price, quantity, manufacturer);
        this.importCountry = importCountry;
        this.usageStatus = usageStatus;
    }

    @Override
    public String toString() {
        return super.toString() + ", Import Country: " + importCountry + ", Usage Status: " + usageStatus;
    }

    @Override
    public String toCSV() {
        return id + "," + name + "," + price + "," + quantity + "," + manufacturer + "," + importCountry + "," + usageStatus;
    }
}
