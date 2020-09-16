
package dtos;

import entities.Car;


public class CarDTO {

    private int id;
    private int year;
    private String make;
    private String model;
    private int price;

    public CarDTO(int id, int year, String make, String model, int price) {
        this.id = id;
        this.year = year;
        this.make = make;
        this.model = model;
        this.price = price;
    }

        public CarDTO(Car car) {
        this.id = car.getId();
        this.year = car.getYear();
        this.make = car.getMake();
        this.model = car.getModel();
        this.price = car.getPrice();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    
}
