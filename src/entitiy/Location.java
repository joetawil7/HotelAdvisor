package entity;

public class Location {

    private String city;
    private int postalCode;

    public Location(){

    }

    public Location(String city, int postalCode) {
        this.city = city;
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Location{" +
                "city='" + city + '\'' +
                ", postalCode=" + postalCode +
                '}';
    }
}