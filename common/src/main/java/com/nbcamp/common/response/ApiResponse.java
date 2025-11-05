package com.nbcamp.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private final HttpStatus httpStatus;

    private final T data;

    public static<T> ApiResponse<T> of(HttpStatus httpStatus) {
        return ApiResponse.<T>builder()
                .httpStatus(httpStatus)
                .build();
    }

    public static<T> ApiResponse<T> of(HttpStatus httpStatus, T data) {
        return ApiResponse.<T>builder()
                .httpStatus(httpStatus)
                .data(data)
                .build();
    }
}
