package com.henrique.pablo.BoardWise.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Set;
import com.henrique.pablo.BoardWise.domain.model.RoleModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModel {

    private String id;
    private String username;
    private String passwordHash;
    private String email;
    private LocalDateTime createdAt;
    private Set<RoleModel> roles;

    public UserModel( String username, String passwordHash, String email, LocalDateTime createdAt) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.createdAt = createdAt;
    }

}
