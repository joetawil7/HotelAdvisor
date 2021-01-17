package entity;

public class Hotel {

    private int hotelId;
    private String hotelName;
    private String address;
    private int postalCode;
    private String city;
    private int phoneNumber;
    private String email;
    private String website;
    private int freeRooms;
    private int stars;
    private double review;
    private int price;
    private String parking;
    private String internet;

    public Hotel(){

    }

    public Hotel(int hotelId, String hotelName, String address, int postalCode, String city, int phoneNumber, String email, String website, int freeRooms, int stars, double review, int price, String parking, String internet) {
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.website = website;
        this.freeRooms = freeRooms;
        this.stars = stars;
        this.review = review;
        this.price = price;
        this.parking = parking;
        this.internet = internet;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public int getFreeRooms() {
        return freeRooms;
    }

    public void setFreeRooms(int freeRooms) {
        this.freeRooms = freeRooms;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public double getReview() {
        return review;
    }

    public void setReview(double review) {
        this.review = review;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    public String getInternet() {
        return internet;
    }

    public void setInternet(String internet) {
        this.internet = internet;
    }

    public String toString() {
        return hotelName + ", " +
                address + ", " +
                postalCode + ", " +
                city + ", " +
                "+49" + phoneNumber + ", " +
                email + ", " +
                website +
                ", Free Rooms= " +freeRooms +
                ", Stars= " +stars +
                ", Review= " + review +
                ", Price= " + price + "$" +
                ", parking='" + parking.toLowerCase() + '\'' +
                ", internet='" + internet.toLowerCase() + '\'';
    }
}