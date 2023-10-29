package com.gp.KuryeNet.dataAccess.abstracts;

import java.util.Date;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.gp.KuryeNet.entities.concretes.Order;
import com.gp.KuryeNet.entities.dtos.OrderWithCourierDto;

@Repository
@Component
public interface OrderDao extends JpaRepository<Order,Integer>{
	
	List<Order> getByOrderDate(Date orderDate);
	
	List<Order> getByOrderType(String orderType);
	
	List<Order> getByOrderStatus(int orderStatus);
	
	Order getByOrderNumber(String orderNumber);
	
	List<Order> getByOrderDateAndOrderStatus(Date orderDate, int OrderStatus);
	
	List<Order> getByCourier_CourierIdIn(List<Integer> couriers);
	
	List<Order> getByOrderNumberContains(String orderNumber);
	
	@Query("FROM Order WHERE EXTRACT(HOUR FROM order_date) = :orderHour AND order_status = :orderStatus")
	List<Order> getByOrderHourAndOrderStatus(int orderHour, int orderStatus);
	
	@Query("FROM Order WHERE EXTRACT(HOUR FROM order_date) = :orderHour")
	List<Order> getByOrderHour(int orderHour);
	
	@Query("SELECT NEW com.gp.KuryeNet.entities.dtos.OrderWithCourierDto(o.orderId,o.orderNumber,c.courierName) FROM Courier c INNER JOIN c.orders o" )
	List<OrderWithCourierDto> getOrderWithCourierDetails();
	
	// select o.order_id,o.order_number,c.courier_name from courier c inner join orders o on c.courier_id = o.courier_id
}
