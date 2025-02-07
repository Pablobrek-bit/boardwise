package com.henrique.pablo.BoardWise.application.service;

import com.henrique.pablo.BoardWise.domain.repository.IProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final IProjectRepository projectRepository;

    // BASIC
    // Create a new project.
    // List all projects (with pagination).
    // Get a project by id.
    // Update a project.
    // Delete a project (soft delete).

    // PROJECT MEMBERS
    // Add a member to the project.
    // Remove a member from the project.
    // List all members of the project (with pagination).
}
