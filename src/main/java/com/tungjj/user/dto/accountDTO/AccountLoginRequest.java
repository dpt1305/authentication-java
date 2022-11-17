package com.tungjj.user.dto.accountDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountLoginRequest {
    private String password;
    private String username;
}
