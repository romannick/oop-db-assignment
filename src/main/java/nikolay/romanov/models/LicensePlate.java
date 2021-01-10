package nikolay.romanov.models;

//CREATE TABLE LicensePlates (
//        id int NOT NULL AUTO_INCREMENT,
//        license varchar(255),
//        country varchar(255),
//        region varchar(255),
//        PRIMARY KEY (id)
//);

public class LicensePlate {
    private final int id;
    private final String license;
    private final String country;
    private final String region;

    public LicensePlate(int id, String license, String country, String region) {
        this.id = id;
        this.license = license;
        this.country = country;
        this.region = region;
    }

    public int getId() {
        return id;
    }

    public String getLicense() {
        return license;
    }

    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }

    @Override
    public String toString() {
        return "LicensePlate{" +
                "id=" + id +
                ", license='" + license + '\'' +
                ", country='" + country + '\'' +
                ", region='" + region + '\'' +
                '}';
    }
}
