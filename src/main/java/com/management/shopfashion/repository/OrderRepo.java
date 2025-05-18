package com.management.shopfashion.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.management.shopfashion.entity.Order;

public interface OrderRepo extends JpaRepository<Order, Integer> {
@Query("Select o from Order o ")
public Page<Order>getPageOrder(Pageable pageable);
@Query("Select o from Order o where user.id_user=?1")
public List<Order>getAllOrderByIdUser(int id);
@Query("SELECT FUNCTION('MONTH', FUNCTION('STR_TO_DATE', o.dateFinal, '%d-%m-%Y %H:%i:%s')) AS month, " +
        "SUM(o.totalPrice) " +
        "FROM Order o " +
        "WHERE o.status = 4 AND FUNCTION('YEAR', FUNCTION('STR_TO_DATE', o.dateFinal, '%d-%m-%Y %H:%i:%s')) = :year " +
        "GROUP BY month ORDER BY month")
List<Object[]> getMonthlyRevenueByYear(@Param("year") int year);

@Query("SELECT FUNCTION('DATE_FORMAT', FUNCTION('STR_TO_DATE', o.dateFinal, '%d-%m-%Y %H:%i:%s'), '%d-%m-%Y') AS date, " +
        "SUM(o.totalPrice) " +
        "FROM Order o " +
        "WHERE o.status = 4 AND FUNCTION('DATE_FORMAT', FUNCTION('STR_TO_DATE', o.dateFinal, '%d-%m-%Y %H:%i:%s'), '%m-%Y') = :month " +
        "GROUP BY date ORDER BY date")
List<Object[]> getDailyRevenueByMonth(@Param("month") String month);
}
