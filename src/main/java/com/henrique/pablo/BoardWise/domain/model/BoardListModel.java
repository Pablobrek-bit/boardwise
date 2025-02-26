package com.henrique.pablo.BoardWise.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardListModel {
    private Integer id;
    private String name;
    private Integer position;
    private Set<ProjectModel> projects;
}
