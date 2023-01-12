package com.stackroute.project.controller;

import com.stackroute.project.QRTemplate;
import com.stackroute.project.exception.ProjectNotFoundException;
import com.stackroute.project.model.Project;
import com.stackroute.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins="*")
@Controller
@RequestMapping("api/v1/qr")
public class QRLinkController
{

    @Autowired
    private ProjectService projectService;

    @GetMapping("/{projectId}")
    public String qr(@PathVariable String projectId, Model model) throws ProjectNotFoundException
    {
        QRTemplate qrTemplate = new QRTemplate("qrpage.html");
        Map<String, String> qrreplacements = new HashMap<String, String>();

        Project project=this.projectService.getProjectById(projectId);
        model.addAttribute("projectName", project.getProjectName());
        model.addAttribute("qrCode",project.getProjectQR());
        return "qrpage";
    }
}
