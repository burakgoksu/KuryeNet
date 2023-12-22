package com.gp.KuryeNet.core.business.concretes.check;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gp.KuryeNet.business.concretes.check.BaseCheckManager;
import com.gp.KuryeNet.core.business.abstracts.check.GoogleMapsAPICheckService;
import com.gp.KuryeNet.core.dataAccess.UserDao;
import com.gp.KuryeNet.core.utulities.Util.Msg;
import com.gp.KuryeNet.dataAccess.abstracts.CourierDao;
import com.gp.KuryeNet.dataAccess.abstracts.OrderDao;

@Service
public class GoogleMapsAPICheckManager extends BaseCheckManager implements GoogleMapsAPICheckService{

	private CourierDao courierDao;
	private OrderDao orderDao;
	
	@Autowired
	public GoogleMapsAPICheckManager(CourierDao courierDao, OrderDao orderDao) {
		super();
		this.courierDao = courierDao;
		this.orderDao = orderDao;
	}

	@Override
	public void isOrderInDistribution(int orderId) {
		if(orderDao.getByOrderId(orderId).getOrderStatus() == 100) {
			errors.put("orderId", Msg.IS_NOT_IN_DISTRIBUTION.get());
		}
		if(orderDao.getByOrderId(orderId).getOrderStatus() == 300) {
			errors.put("orderId", Msg.IS_DELIVERED.get());
		}
		
	}

	@Override
	public void isCourierInDistribution(String courierEmail) {
		if(courierDao.getByCourierEmail(courierEmail).getCourierStatus() != 200)
			errors.put("courierId", Msg.IS_NOT_IN_DISTRIBUTION.get());
		
	}

}
