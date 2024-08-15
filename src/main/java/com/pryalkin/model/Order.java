package com.pryalkin.model;

import java.util.Objects;

public class Order {

    private String id;
    private String status;
    private String carId;
    private String userId;

    @Override
    public String toString() {
        return "{" +
                "id:'" + id + '\'' +
                ", status:'" + status + '\'' +
                ", idCar:'" + carId + '\'' +
                ", idUser:'" + userId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(status, order.status) && Objects.equals(carId, order.carId) && Objects.equals(userId, order.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, carId, userId);
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getIdCar() {
        return carId;
    }

    public String getIdUser() {
        return userId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setIdCar(String idCar) {
        this.carId = idCar;
    }

    public void setIdUser(String idUser) {
        this.userId = idUser;
    }
}
