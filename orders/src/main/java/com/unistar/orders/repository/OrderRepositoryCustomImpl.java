package com.unistar.orders.repository;

import com.unistar.orders.entity.OrderEntity;
import com.unistar.orders.model.OrderType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<OrderEntity> findOrderByOrderTypeAndDueDate(OrderType orderType, LocalDate dueDate) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<OrderEntity> query = cb.createQuery(OrderEntity.class);
        Root<OrderEntity> order = query.from(OrderEntity.class);
        List<Predicate> predicates = new ArrayList<>();

        if(orderType != null) {
            Path<OrderType> orderTypePath = order.get("orderType");
            predicates.add(cb.equal(orderTypePath, orderType));
        }

        if(dueDate != null) {
            Path<LocalDate> dueDatePath = order.get("dueDate");
            predicates.add(cb.equal(dueDatePath, dueDate));
        }

        query.select(order).where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        return entityManager.createQuery(query).getResultList();
    }
}
