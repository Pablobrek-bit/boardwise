package com.henrique.pablo.BoardWise.domain.model;

import com.henrique.pablo.BoardWise.application.dto.project.ProjectRequest;
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
    private BoardListModel boardLists;

    public void updateFields(ProjectRequest request){
        if(request.name() != null){
            this.setName(request.name());
        }
        if(request.description() != null){
            this.setDescription(request.description());
        }
    }
}
