package com.drone.drone.dto;

import lombok.Data;

@Data
public class ResponseDto {
    private boolean status;
    private String message;
    private int code;

    public ResponseDto(boolean status, String message, int code) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
