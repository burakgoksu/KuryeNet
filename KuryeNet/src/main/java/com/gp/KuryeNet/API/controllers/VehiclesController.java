package com.gp.KuryeNet.API.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gp.KuryeNet.business.abstracts.VehicleService;
import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.Result;
import com.gp.KuryeNet.entities.concretes.Vehicle;

@RestController
@RequestMapping("/api/vehicles")
public class VehiclesController {
	
	private VehicleService vehicleService;
	
	@Autowired
	public VehiclesController(VehicleService vehicleService) {
		super();
		this.vehicleService = vehicleService;
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody Vehicle vehicle){
		return this.vehicleService.add(vehicle);
		
	}
	
	@GetMapping("/getall")
	public DataResult<List<Vehicle>> getAll(){
		return this.vehicleService.getAll();
		
	}
	
	@GetMapping("/getallByPage")
	public DataResult<List<Vehicle>> getAll(int pageNo,int pageSize){
		return this.vehicleService.getAll(pageNo,pageSize);

	}
	
	@GetMapping("/getAllSortedByBrand")
	public DataResult<List<Vehicle>> getAllSortedByBrand(){
		return this.vehicleService.getAllSortedByBrand();
	}
	
	@GetMapping("/getAllSortedByYear")
	public DataResult<List<Vehicle>> getAllSortedByYear(){
		return this.vehicleService.getAllSortedByYear();
	}
	
	@GetMapping("/getByVehiclePlate")
	public DataResult<Vehicle> getByVehiclePlate(String vehiclePlate){
		return this.vehicleService.getByVehiclePlate(vehiclePlate);
	}
	
	@GetMapping("/getByVehicleType")
	public DataResult<List<Vehicle>> getByVehicleType(String vehicleType){
		return this.vehicleService.getByVehicleType(vehicleType);
	}
	
	@GetMapping("/getByVehicleBrand")
	public DataResult<List<Vehicle>> getByVehicleBrand(String vehicleBrand){
		return this.vehicleService.getByVehicleBrand(vehicleBrand);
	}
	
	@GetMapping("/getByVehicleModel")
	public DataResult<List<Vehicle>> getByVehicleModel(String vehicleModel){
		return this.vehicleService.getByVehicleModel(vehicleModel);
	}
	
	@GetMapping("/getByVehicleYear")
	public DataResult<List<Vehicle>> getByVehicleYear(int vehicleYear){
		return this.vehicleService.getByVehicleYear(vehicleYear);
	}
	
	@GetMapping("/getByCourierId")
	public DataResult<List<Vehicle>> getByCouriers_CourierId(int courierId){
		return this.vehicleService.getByCouriers_CourierId(courierId);
	}
	
	@GetMapping("/getByCourierIdentityNumber")
	public DataResult<List<Vehicle>> getByCouriers_CourierIdentityNumber(String courierIdentityNumber){
		return this.vehicleService.getByCouriers_CourierIdentityNumber(courierIdentityNumber);
	}
	
	@GetMapping("/getByCourierNameAndCourierSurname")
	public DataResult<List<Vehicle>> getByCouriers_CourierNameAndCouriers_CourierSurname(String courierName, String courierSurname){
		return this.vehicleService.getByCouriers_CourierNameAndCouriers_CourierSurname(courierName,courierSurname);
	}
	
	

}
