package com.gp.KuryeNet.entities.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.gp.KuryeNet.entities.concretes.Address;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {

	@NotNull
	@NotBlank
	@Schema(example = "Ataturk Mahallesi, No:10")
	private String address;

	@NotNull
	@NotBlank
	@Schema(example = "Home")
	private String addressTitle;

	@NotNull
	@NotBlank
	@Schema(example = "Manisa")
	private String city;

	@NotNull
	@NotBlank
	@Schema(example = "Yunus Emre")
	private String district;

	@NotNull
	@NotBlank
	@Schema(example = "Muradiye")
	private String street;

	@Schema(example = "2")
	private String buildingNumber;
	@Schema(example = "5")
	private String floorNumber;
	@Schema(example = "12")
	private String apartmentNumber;

	@NotNull
	@NotBlank
	@Schema(example = "05458624532")
	private String phoneNumber;

	@Schema(example = "123")
	private Integer addressId;

    public Address toEntity() {
        Address addressEntity = new Address();
        if (addressId != null) {
            addressEntity.setAddressId(addressId);
        }
        addressEntity.setAddress(address);
        addressEntity.setAddressTitle(addressTitle);
        addressEntity.setCity(city);
        addressEntity.setDistrict(district);
        addressEntity.setStreet(street);
        addressEntity.setBuildingNumber(buildingNumber);
        addressEntity.setFloorNumber(floorNumber);
        addressEntity.setApartmentNumber(apartmentNumber);
        addressEntity.setPhoneNumber(phoneNumber);
        return addressEntity;
    }
}
