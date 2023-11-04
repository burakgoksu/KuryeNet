package com.gp.KuryeNet.API.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gp.KuryeNet.business.abstracts.VehicleService;
import com.gp.KuryeNet.core.utulities.Util.Utils;
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
	public ResponseEntity<?> add(@Valid @RequestBody Vehicle vehicle){
		return Utils.getResponseEntity(this.vehicleService.add(vehicle));
		
	}
	
	@GetMapping("/getall")
	public ResponseEntity<?> getAll(){
		return Utils.getResponseEntity(this.vehicleService.getAll());
		
	}
	
	@GetMapping("/getallByPage")
	public ResponseEntity<?> getAll(@RequestParam int pageNo,@RequestParam int pageSize){
		return Utils.getResponseEntity(this.vehicleService.getAll(pageNo,pageSize));

	}
	
	@GetMapping("/getAllSortedByBrand")
	public ResponseEntity<?> getAllSortedByBrand(){
		return Utils.getResponseEntity(this.vehicleService.getAllSortedByBrand());
	}
	
	@GetMapping("/getAllSortedByYear")
	public ResponseEntity<?> getAllSortedByYear(){
		return Utils.getResponseEntity(this.vehicleService.getAllSortedByYear());
	}
	
	@GetMapping("/getByVehiclePlate")
	public ResponseEntity<?> getByVehiclePlate(@RequestParam String vehiclePlate){
		return Utils.getResponseEntity(this.vehicleService.getByVehiclePlate(vehiclePlate));
	}
	
	@GetMapping("/getByVehicleType")
	public ResponseEntity<?> getByVehicleType(@RequestParam String vehicleType){
		return Utils.getResponseEntity(this.vehicleService.getByVehicleType(vehicleType));
	}
	
	@GetMapping("/getByVehicleBrand")
	public ResponseEntity<?> getByVehicleBrand(@RequestParam String vehicleBrand){
		return Utils.getResponseEntity(this.vehicleService.getByVehicleBrand(vehicleBrand));
	}
	
	@GetMapping("/getByVehicleModel")
	public ResponseEntity<?> getByVehicleModel(@RequestParam String vehicleModel){
		return Utils.getResponseEntity(this.vehicleService.getByVehicleModel(vehicleModel));
	}
	
	@GetMapping("/getByVehicleYear")
	public ResponseEntity<?> getByVehicleYear(@RequestParam int vehicleYear){
		return Utils.getResponseEntity(this.vehicleService.getByVehicleYear(vehicleYear));
	}
	
	@GetMapping("/getByCourierId")
	public ResponseEntity<?> getByCouriers_CourierId(@RequestParam int courierId){
		return Utils.getResponseEntity(this.vehicleService.getByCouriers_CourierId(courierId));
	}
	
	@GetMapping("/getByCourierIdentityNumber")
	public ResponseEntity<?> getByCouriers_CourierIdentityNumber(@RequestParam String courierIdentityNumber){
		return Utils.getResponseEntity(this.vehicleService.getByCouriers_CourierIdentityNumber(courierIdentityNumber));
	}
	
	@GetMapping("/getByCourierNameAndCourierSurname")
	public ResponseEntity<?> getByCouriers_CourierNameAndCouriers_CourierSurname(@RequestParam String courierName,@RequestParam String courierSurname){
		return Utils.getResponseEntity(this.vehicleService.getByCouriers_CourierNameAndCouriers_CourierSurname(courierName,courierSurname));
	}
	
	@GetMapping("/getByVehicleId")
	public ResponseEntity<?> getByVehicleId(@RequestParam int vehicleId){
		return Utils.getResponseEntity(this.vehicleService.getByVehicleId(vehicleId));
	}
	
	

}
