package com.henrique.pablo.BoardWise.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

    public void addRole(RoleModel role){
        if(roles == null){
            roles = new HashSet<>();
        }
        roles.add(role);
    }

}
