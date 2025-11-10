package com.mcargo.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private final ResponseCode responseCode;

    private final T data;

    public static<T> ApiResponse<T> of(ResponseCode responseCode) {
        return ApiResponse.<T>builder()
                .responseCode(responseCode)
                .build();
    }

    public static<T> ApiResponse<T> of(ResponseCode responseCode, T data) {
        return ApiResponse.<T>builder()
                .responseCode(responseCode)
                .data(data)
                .build();
    }
}
