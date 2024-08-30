package com.pryalkin.model;

import lombok.Data;

import java.util.Objects;

@Data
public class Order {

    private Long id;
    private String status;
    private Car car;
    private UserShop userShop;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(status, order.status) && Objects.equals(car, order.car) && Objects.equals(userShop, order.userShop);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, car, userShop);
    }

}
