package com.gp.KuryeNet.entities.dtos;

import com.gp.KuryeNet.entities.concretes.Address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    private int addressId;
    private String address;
    private String addressTitle;
    private String city;
    private String district;
    private String street;
    private String buildingNumber;
    private String floorNumber;
    private String apartmentNumber;
    private String phoneNumber;

    public static AddressDto fromEntity(Address address) {
        if (address == null) {
            return null;
        }
        return new AddressDto(
                address.getAddressId(),
                address.getAddress(),
                address.getAddressTitle(),
                address.getCity(),
                address.getDistrict(),
                address.getStreet(),
                address.getBuildingNumber(),
                address.getFloorNumber(),
                address.getApartmentNumber(),
                address.getPhoneNumber()
        );
    }
}
