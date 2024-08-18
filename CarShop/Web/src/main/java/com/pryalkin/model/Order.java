package com.pryalkin.model;

import java.util.Objects;

public class Order {

    private Integer id;
    private String status;
    private Car car;
    private User user;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(status, order.status) && Objects.equals(car, order.car) && Objects.equals(user, order.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, car, user);
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public Car getCar() {
        return car;
    }

    public User getUser() {
        return user;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setUser(User user) {
        this.user= user;
    }
}
