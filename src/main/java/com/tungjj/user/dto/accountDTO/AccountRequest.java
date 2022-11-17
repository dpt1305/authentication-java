package com.tungjj.user.dto.accountDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {
    private String username;
    private String password;
    private String name;
    private int age;
    private boolean deleted;
}
