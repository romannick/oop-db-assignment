package nikolay.romanov.models;

//CREATE TABLE Cars (
//        id int NOT NULL AUTO_INCREMENT,
//        owner_id int NOT NULL,
//        brand varchar(255),
//        model varchar(255),
//        year int,
//        horse_power int,
//        license_plate_id int NOT NULL,
//        PRIMARY KEY (id),
//        FOREIGN KEY (owner_id) REFERENCES People(id),
//        FOREIGN KEY (license_plate_id) REFERENCES LicensePlates(id)
//);

public class Car {
    private final int id;
    private final Person owner;
    private final String brand;
    private final String model;
    private final int year;
    private final int horsePower;
    private final LicensePlate licensePlate;

    public Car(int id, Person owner, String brand, String model, int year, int horsePower, LicensePlate licensePlate) {
        this.id = id;
        this.owner = owner;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.horsePower = horsePower;
        this.licensePlate = licensePlate;
    }

    public int getId() {
        return id;
    }

    public Person getOwner() {
        return owner;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public LicensePlate getLicensePlate() {
        return licensePlate;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", owner=" + owner +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", horsePower=" + horsePower +
                ", licensePlate=" + licensePlate +
                '}';
    }
}
