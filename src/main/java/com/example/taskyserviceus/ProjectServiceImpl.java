package com.example.taskyserviceus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(Project project) {
        // Implement the update logic
        return projectRepository.save(project);
    }

    @Override
    public void deleteProject(Project project) {
        // Implement the delete logic
        projectRepository.delete(project);
    }

    @Override
    public void deleteProjectById(Long id) {
        // Implement the delete logic by ID
        projectRepository.deleteById(id);
    }

    @Override
    public Project getProject(Long id) {
        return projectRepository.findById(id).orElse(null);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Page<Project> getAllProjectsParPage(int page, int size) {
        return projectRepository.findAll(PageRequest.of(page, size));
    }
}
