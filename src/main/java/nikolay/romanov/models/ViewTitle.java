package nikolay.romanov.models;

public enum ViewTitle {
    PEOPLE_VIEW("People table"),
    LICENSE_PLATES_VIEW("License plates table"),
    CARS_VIEW("Cars table");

    private final String value;

    ViewTitle(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
