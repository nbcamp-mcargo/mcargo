package com.mcargo.hubservice.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record CreateHubRequest(

        @NotBlank(message = "허브 이름은 비어 있을 수 없습니다.")
        String name,

        @NotBlank(message = "주소는 비어 있을 수 없습니다.")
        String address,

        @NotNull(message = "위도는 필수 입력값입니다.")
        @DecimalMin(value = "-90.0", message = "위도는 -90 이상이어야 합니다.")
        @DecimalMax(value = "90.0", message = "위도는 90 이하이어야 합니다.")
        Double latitude,

        @NotNull(message = "경도는 필수 입력값입니다.")
        @DecimalMin(value = "-180.0", message = "경도는 -180 이상이어야 합니다.")
        @DecimalMax(value = "180.0", message = "경도는 180 이하이어야 합니다.")
        Double longitude

) {
}
