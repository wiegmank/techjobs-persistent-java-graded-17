package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.launchcode.techjobs.persistent.models.data.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private SkillController skillRepository;

    @RequestMapping("/")
    public String index(Model model) {

        model.addAttribute("title", "MyJobs");

        return "index";
    }

    @GetMapping("add")
    public String displayAddJobForm(Model model) {
	    model.addAttribute("title", "Add Job");
        model.addAttribute(new Job());
        model.addAttribute("employers", employerRepository.findAll());
        return "add";
    }

    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                                       Errors errors, Model model, @RequestParam int employerId) {
        model.addAttribute("employers", employerRepository.findAll());
        model.addAttribute("employerId", employerId);
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Job");
            return "/add";
        } else {
            Optional optEmployer = employerRepository.findById(employerId);
            Employer employer = (Employer) optEmployer.get();
            newJob.setEmployer(employer);
            jobRepository.save(newJob);

            //employerId/employer.id, job.name

            return "redirect:/index";
        }



//        model.addAttribute("employers", employerRepository.findAll());
//        model.addAttribute("employerId", employerId);
//        if (errors.hasErrors()) {
//            model.addAttribute("title", "Add Job");
//            return "/add";
//        } else {
//
//        //??employerId/employer.id, job.name
//
//        return "redirect:/index";
//    }

}


    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable int jobId) {

        //model.addAttribute("job", jobRepository.findById(jobId));

            return "view";
    }

}
