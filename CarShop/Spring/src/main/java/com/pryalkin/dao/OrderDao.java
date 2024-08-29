package com.pryalkin.dao;

import com.pryalkin.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderDao extends Dao<Order, Long>{

    Optional<List<Order>> findByUserRole(String role);
}
