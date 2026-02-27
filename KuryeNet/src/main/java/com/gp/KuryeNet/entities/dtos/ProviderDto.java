package com.gp.KuryeNet.entities.dtos;

import com.gp.KuryeNet.entities.concretes.Provider;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProviderDto {

    private int providerId;
    private String providerType;
    private String providerName;
    private double providerLatitude;
    private double providerLongitude;
    private String providerMersisNo;
    private AddressDto providerAddress;

    public static ProviderDto fromEntity(Provider provider) {
        if (provider == null) {
            return null;
        }
        AddressDto address = provider.getProviderAddress() != null
                ? AddressDto.fromEntity(provider.getProviderAddress())
                : null;
        return new ProviderDto(
                provider.getProviderId(),
                provider.getProviderType(),
                provider.getProviderName(),
                provider.getProviderLatitude(),
                provider.getProviderLongitude(),
                provider.getProviderMersisNo(),
                address
        );
    }
}
