package com.pryalkin.model;

import java.util.Objects;

public class Order {

    private String id;
    private String status;
    private String idCar;
    private String idUser;

    @Override
    public String toString() {
        return "{" +
                "id:'" + id + '\'' +
                ", status:'" + status + '\'' +
                ", idCar:'" + idCar + '\'' +
                ", idUser:'" + idUser + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(status, order.status) && Objects.equals(idCar, order.idCar) && Objects.equals(idUser, order.idUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, idCar, idUser);
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getIdCar() {
        return idCar;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setIdCar(String idCar) {
        this.idCar = idCar;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
