package com.henrique.pablo.BoardWise.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectModel {
    private String id;
    private String name;
    private String description;
    private Boolean deleted;
    private UserModel owner;
    private Set<UserModel> participants = new HashSet<>();
}
