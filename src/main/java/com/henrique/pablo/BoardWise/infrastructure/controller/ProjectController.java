package com.henrique.pablo.BoardWise.infrastructure.controller;

import com.henrique.pablo.BoardWise.application.dto.project.ProjectRequest;
import com.henrique.pablo.BoardWise.application.dto.project.ProjectResponse;
import com.henrique.pablo.BoardWise.application.dto.project.ProjectResponseWithParticipants;
import com.henrique.pablo.BoardWise.application.dto.user.UserResponse;
import com.henrique.pablo.BoardWise.application.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    // BASIC
    // Create a new project.
    @PostMapping
    public ProjectResponse createProject(@RequestAttribute("id") String ownerId,
                                         @Valid @RequestBody ProjectRequest projectRequest){
        return projectService.createProject(ownerId, projectRequest);
    }

    // List all projects (with pagination).
    @GetMapping
    public Page<ProjectResponse> findAll(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "id") String sort,
                                      @RequestParam(defaultValue = "asc") String direction,
                                      @RequestParam(defaultValue = "") String search,
                                      @RequestAttribute("id") String ownerId
                                         ){
        return projectService.listProjects(page, size, sort, direction, ownerId, search);
    }

    // Get a project by id.
    @GetMapping("/{id}")
    public ProjectResponse findById(@PathVariable String id, @RequestAttribute("id") String ownerId){
        return projectService.findProjectById(id, ownerId);
    }

    // Update a project
    @PutMapping("/{id}")
    public ProjectResponse updateProject(@PathVariable String id,
                                         @RequestAttribute("id") String ownerId,
                                         @Valid @RequestBody ProjectRequest projectRequest){
        return projectService.updateProject(id, ownerId, projectRequest);
    }

    // Delete a project (soft delete).
    @DeleteMapping("/{id}")
    public ProjectResponse deleteProject(@PathVariable String id,
                                         @RequestAttribute("id") String ownerId){
        return projectService.deleteProject(id, ownerId);
    }

    // PROJECT MEMBERS
    @PostMapping("/{id}/members")
    public ProjectResponseWithParticipants addMember(@PathVariable String id,
                                                     @RequestAttribute("id") String ownerId,
                                                     @RequestParam String memberId){
        return projectService.addMember(id, ownerId, memberId);
    }

    // Remove a member from the project.
    @PostMapping("/{id}/members/{memberId}")
    public ProjectResponseWithParticipants removeMember(@PathVariable String id,
                                                        @PathVariable String memberId,
                                                        @RequestAttribute("id") String ownerId){
        return projectService.removeMember(id, memberId, ownerId);
    }

    // List all members of the project (with pagination).
    @GetMapping("/{id}/members")
    public Page<UserResponse> listMembers(@PathVariable String id,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size,
                                                            @RequestParam(defaultValue = "id") String sort,
                                                            @RequestParam(defaultValue = "asc") String direction,
                                                            @RequestAttribute("id") String ownerId){
        return projectService.listMembers(id, page, size, sort, direction, ownerId);
    }

}
