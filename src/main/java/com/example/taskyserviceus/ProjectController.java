package com.example.taskyserviceus;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class ProjectController {

   /* @Autowired
    RestTemplate restTemplate;
*/
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    ProjectService projectService;

    @GetMapping("/")
    //@PreAuthorize("hasRole('client-user')")
    public String index(){
        return "index";
    }


    @RequestMapping("/showCreateProject")
    public String showCreateProject(ModelMap modelMap)
    {
        modelMap.addAttribute("s", new Project());
        return "createProject";
    }


    @RequestMapping("/saveProject")
    public String saveProject(@ModelAttribute("s") Project project,
                              BindingResult bindingResult,
                              ModelMap modelMap) throws ParseException {

        if (bindingResult.hasErrors()) {
            return "createProject";
        }

        // Check if the date is not null and not empty
        if (project.getDate() == null) {
            // Set a default date or handle it as needed
            project.setDate(new Date());
        }

        Project saveProject = projectService.saveProject(project);
        String msg = "Project saved with Id " + saveProject.getId();
        modelMap.addAttribute("msg", msg);

        return "createProject";
    }


    @RequestMapping("/listeProjects")
    public String listeProjects(ModelMap modelMap, @RequestParam(name = "page", defaultValue = "0") int page,
                                @RequestParam(name = "size", defaultValue = "10") int size) {
        Page<Project> projects = projectService.getAllProjectsParPage(page, size);
        modelMap.addAttribute("projects", projects);
        modelMap.addAttribute("pages", new int[projects.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        return "listeProjects"; // Specify the correct HTML file name here
    }




    @RequestMapping("/supprimerProject")
    public String supprimerProject(@RequestParam("id") Long id, ModelMap modelMap, @RequestParam(name = "page", defaultValue = "0") int page,
                                   @RequestParam(name = "size", defaultValue = "10") int size)
    {
        projectService.deleteProjectById(id);
        Page<Project> projects = projectService.getAllProjectsParPage(page, size);
        modelMap.addAttribute("projects", projects);
        modelMap.addAttribute("pages", new int[projects.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        modelMap.addAttribute("size", size);
        return "listeProjects";
    }


    @RequestMapping("/modifierProject")
    public String editProject(@RequestParam("id") Long id,ModelMap modelMap,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "size", defaultValue = "2") int size)
    {

        Project project = projectService.getProject(id);
        modelMap.addAttribute("s", project);
        modelMap.addAttribute("currentPage", page);
        modelMap.addAttribute("size", size);
        return "editProject";
    }
    @Autowired
    private UserRepository userRepository;

    // ... existing methods ...

    @GetMapping("/showCreateUser")
    public String showCreateUser(ModelMap modelMap) {
        modelMap.addAttribute("s", new User());
        return "createUser";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("s") @Valid User user,
                           BindingResult bindingResult,
                           ModelMap modelMap) {
        if (bindingResult.hasErrors()) {
            return "createUser";
        }

        // Simulating saving a user
        // Modify this part to save the user using your UserService
        userRepository.save(user);

        String msg = "User saved with Id " + user.getId();
        modelMap.addAttribute("msg", msg);

        // Add the list of users to the model
        List<User> userList = userRepository.findAll();
        modelMap.addAttribute("userList", userList);

        return "createUser";
    }

    @GetMapping("/listeUsers")
    public String listeUsers(ModelMap modelMap) {
        // Retrieve the list of users from the repository
        List<User> userList = userRepository.findAll();

        modelMap.addAttribute("userList", userList);
        return "listeUsers";
    }

    @GetMapping("/listAllUsers")
    public String listAllUsers(ModelMap modelMap) {
        // Retrieve the list of users from the repository
        List<User> userList = userRepository.findAll();

        modelMap.addAttribute("userList", userList);
        return "listAllUsers";
    }


    @RequestMapping("/updateProject")
    public String updateProject(@ModelAttribute("s") Project new_project,
                                @RequestParam("date") String date,
                                @RequestParam(name = "page", defaultValue = "0") int page,
                                @RequestParam(name = "size", defaultValue = "7") int size,
                                ModelMap modelMap) throws ParseException {

        Project old_project = projectService.getProject(new_project.getId());

        if (!date.isEmpty() && !old_project.getDate().equals(new_project.getDate())) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = dateFormat.parse(date);
            old_project.setDate(parsedDate);
        }

        if (!old_project.getName().equals(new_project.getName()) && new_project.getName().length() > 0) {
            old_project.setName(new_project.getName());
        }

        projectService.updateProject(old_project);

        // Populate the modelMap as needed
        Page<Project> projects = projectService.getAllProjectsParPage(page, size);
        modelMap.addAttribute("projects", projects);
        modelMap.addAttribute("pages", new int[projects.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        modelMap.addAttribute("size", size);

        String msg = "Project updated";
        modelMap.addAttribute("msg", msg);

        return "listeProjects";
    }






    /////*******************************************************
    //////*******************Task*******************************
    //**********************************************************

}
