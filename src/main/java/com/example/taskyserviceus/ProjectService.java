package com.example.taskyserviceus;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProjectService {
    Project saveProject(Project s);
    Project updateProject(Project s);
    void deleteProject(Project s);
    void deleteProjectById(Long id);
    Project getProject(Long id);
    List<Project> getAllProjects();
    Page<Project> getAllProjectsParPage(int page, int size);
}
