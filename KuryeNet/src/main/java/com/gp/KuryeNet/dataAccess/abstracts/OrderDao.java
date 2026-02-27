package com.gp.KuryeNet.dataAccess.abstracts;

import java.util.Date;
import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.time.Instant;

import com.gp.KuryeNet.entities.concretes.Address;
import com.gp.KuryeNet.entities.concretes.Order;
import com.gp.KuryeNet.entities.dtos.OrderWithAddressDto;
import com.gp.KuryeNet.entities.dtos.OrderWithCourierDto;
import com.gp.KuryeNet.entities.dtos.StartOrderWithCourierDto;

@Repository
@Component
@Transactional
public interface OrderDao extends JpaRepository<Order,Integer>{
	
	List<Order> getByOrderDate(Date orderDate);
	
	List<Order> getByOrderType(String orderType);
	
	List<Order> getByOrderStatus(int orderStatus);
	
	Order getByOrderNumber(String orderNumber);
	
	Order getByOrderId(int orderId);
	
	boolean existsByOrderNumber(String orderNumber);
	
	List<Order> getByOrderDateAndOrderStatus(Date orderDate, int OrderStatus);
	
	List<Order> getByCourier_CourierIdIn(List<Integer> couriers);
	
	List<Order> getByOrderAddress_AddressId(int addressId);
	
	List<Order> getByOrderNumberContains(String orderNumber);
	
	@Query("FROM Order WHERE EXTRACT(HOUR FROM orderDate) = :orderHour AND orderStatus = :orderStatus")
	List<Order> getByOrderHourAndOrderStatus(int orderHour, int orderStatus);
	
	@Query("FROM Order WHERE EXTRACT(HOUR FROM orderDate) = :orderHour")
	List<Order> getByOrderHour(int orderHour);
	
	@Query("SELECT NEW com.gp.KuryeNet.entities.dtos.OrderWithCourierDto(o.orderId,o.orderNumber,c.courierName,c.courierSurname) FROM Courier c INNER JOIN c.orders o" )
	List<OrderWithCourierDto> getOrderWithCourierDetails();
	
	@Query("SELECT NEW com.gp.KuryeNet.entities.dtos.OrderWithAddressDto"
			+ "(o.orderId,o.orderNumber,o.orderedPlatform,o.orderStatus,o.orderDate,o.deliveryDate,o.estimatedDeliveryTime,o.remainingMinutes,a.address,a.city,a.district,a.street,a.buildingNumber,a.floorNumber,a.apartmentNumber,a.phoneNumber) "
			+ "FROM Address a INNER JOIN a.orders o" )
	List<OrderWithAddressDto> getOrderWithAddressDetails();

	@Query(value = "SELECT * FROM orders WHERE deleted = true", nativeQuery = true)
	List<Order> getDeleted();

	@Modifying
	@Query("UPDATE Order o SET o.deleted = false, o.deletedAt = null, o.deletedBy = null WHERE o.orderId = :id")
	int restoreById(@Param("id") int id);

	@Modifying
	@Query("UPDATE Order o SET o.deleted = true, o.deletedAt = :deletedAt, o.deletedBy = :deletedBy WHERE o.orderId = :id")
	int softDeleteById(@Param("id") int id, @Param("deletedAt") Instant deletedAt, @Param("deletedBy") String deletedBy);
	
	// select o.order_id,o.order_number,c.courier_name from courier c inner join orders o on c.courier_id = o.courier_id
}
