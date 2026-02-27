package com.gp.KuryeNet.API.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import com.gp.KuryeNet.business.abstracts.VehicleService;
import com.gp.KuryeNet.core.utulities.Util.Utils;
import com.gp.KuryeNet.core.utulities.mapper.ResultMapper;
import com.gp.KuryeNet.entities.dtos.VehicleDto;
import com.gp.KuryeNet.entities.dtos.requests.VehicleCreateRequest;

@RestController
@Validated
@RequestMapping(path = { "${api.base-path:/api}/vehicles", "${api.versioned-base-path:/api/v1}/vehicles" })
public class VehiclesController {
	
	private VehicleService vehicleService;
	
	@Autowired
	public VehiclesController(VehicleService vehicleService) {
		super();
		this.vehicleService = vehicleService;
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> add(@Valid @RequestBody VehicleCreateRequest vehicleRequest){
		return Utils.getResponseEntity(this.vehicleService.add(vehicleRequest.toEntity()));
		
	}
	
	@GetMapping("/getall")
	public ResponseEntity<?> getAll(){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.vehicleService.getAll(), VehicleDto::fromEntity));
		
	}
	
	@GetMapping("/getallByPage")
	public ResponseEntity<?> getAll(@RequestParam @Min(0) int pageNo,@RequestParam @Min(1) @Max(200) int pageSize){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.vehicleService.getAll(pageNo,pageSize), VehicleDto::fromEntity));

	}
	
	@GetMapping("/getAllSortedByBrand")
	public ResponseEntity<?> getAllSortedByBrand(){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.vehicleService.getAllSortedByBrand(), VehicleDto::fromEntity));
	}
	
	@GetMapping("/getAllSortedByYear")
	public ResponseEntity<?> getAllSortedByYear(){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.vehicleService.getAllSortedByYear(), VehicleDto::fromEntity));
	}
	
	@GetMapping("/getByVehiclePlate")
	public ResponseEntity<?> getByVehiclePlate(@RequestParam String vehiclePlate){
		return Utils.getResponseEntity(ResultMapper.mapIfSuccess(this.vehicleService.getByVehiclePlate(vehiclePlate), VehicleDto::fromEntity));
	}
	
	@GetMapping("/getByVehicleType")
	public ResponseEntity<?> getByVehicleType(@RequestParam String vehicleType){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.vehicleService.getByVehicleType(vehicleType), VehicleDto::fromEntity));
	}
	
	@GetMapping("/getByVehicleBrand")
	public ResponseEntity<?> getByVehicleBrand(@RequestParam String vehicleBrand){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.vehicleService.getByVehicleBrand(vehicleBrand), VehicleDto::fromEntity));
	}
	
	@GetMapping("/getByVehicleModel")
	public ResponseEntity<?> getByVehicleModel(@RequestParam String vehicleModel){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.vehicleService.getByVehicleModel(vehicleModel), VehicleDto::fromEntity));
	}
	
	@GetMapping("/getByVehicleYear")
	public ResponseEntity<?> getByVehicleYear(@RequestParam int vehicleYear){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.vehicleService.getByVehicleYear(vehicleYear), VehicleDto::fromEntity));
	}
	
	@GetMapping("/getByCourierId")
	public ResponseEntity<?> getByCouriers_CourierId(@RequestParam int courierId){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.vehicleService.getByCouriers_CourierId(courierId), VehicleDto::fromEntity));
	}
	
	@GetMapping("/getByCourierIdentityNumber")
	public ResponseEntity<?> getByCouriers_CourierIdentityNumber(@RequestParam String courierIdentityNumber){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.vehicleService.getByCouriers_CourierIdentityNumber(courierIdentityNumber), VehicleDto::fromEntity));
	}
	
	@GetMapping("/getByCourierNameAndCourierSurname")
	public ResponseEntity<?> getByCouriers_CourierNameAndCouriers_CourierSurname(@RequestParam String courierName,@RequestParam String courierSurname){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.vehicleService.getByCouriers_CourierNameAndCouriers_CourierSurname(courierName,courierSurname), VehicleDto::fromEntity));
	}
	
	@GetMapping("/getByVehicleId")
	public ResponseEntity<?> getByVehicleId(@RequestParam int vehicleId){
		return Utils.getResponseEntity(ResultMapper.mapIfSuccess(this.vehicleService.getByVehicleId(vehicleId), VehicleDto::fromEntity));
	}

	@DeleteMapping("/delete")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> delete(@RequestParam int vehicleId){
		return Utils.getResponseEntity(this.vehicleService.delete(vehicleId));
	}

	@PostMapping("/restore")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> restore(@RequestParam int vehicleId){
		return Utils.getResponseEntity(this.vehicleService.restore(vehicleId));
	}

	@GetMapping("/deleted")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> getDeleted(){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.vehicleService.getDeleted(), VehicleDto::fromEntity));
	}
	
	

}

