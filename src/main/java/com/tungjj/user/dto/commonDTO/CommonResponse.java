package com.tungjj.user.dto.commonDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse<T> {
    private boolean success;
    private T result;
    private String message;
    private int statusCode;
}