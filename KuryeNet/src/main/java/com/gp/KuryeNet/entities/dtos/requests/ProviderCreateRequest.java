package com.gp.KuryeNet.entities.dtos.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.gp.KuryeNet.entities.concretes.Provider;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProviderCreateRequest {

    @NotNull
    @Schema(example = "1")
    private Integer providerId;

    @NotNull
    @NotBlank
    @Schema(example = "restoran")
    private String providerType;

    @NotNull
    @NotBlank
    @Schema(example = "Kofteci Yusuf")
    private String providerName;

    @Schema(example = "38.7412")
    private double providerLatitude;
    @Schema(example = "27.2296")
    private double providerLongitude;
    @Schema(example = "1234567890123456")
    private String providerMersisNo;

    @Valid
    private AddressRequest providerAddress;

    public Provider toEntity() {
        Provider provider = new Provider();
        if (providerId != null) {
            provider.setProviderId(providerId);
        }
        provider.setProviderType(providerType);
        provider.setProviderName(providerName);
        provider.setProviderLatitude(providerLatitude);
        provider.setProviderLongitude(providerLongitude);
        provider.setProviderMersisNo(providerMersisNo);
        if (providerAddress != null) {
            provider.setProviderAddress(providerAddress.toEntity());
        }
        return provider;
    }
}
