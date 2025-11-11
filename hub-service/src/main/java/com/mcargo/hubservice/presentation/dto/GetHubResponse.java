package com.mcargo.hubservice.presentation.dto;

public record GetHubResponse(

        String name,

        String address,

        Double latitude,

        Double longitude,

        Integer lastDriverNumber
) {
}
