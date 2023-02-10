package com.webmonitor.webmon.controllers;

import com.webmonitor.webmon.models.Website;
import com.webmonitor.webmon.services.AuthenticationService;
import com.webmonitor.webmon.services.WebsiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

//@RestController
//@RequestMapping("/")

@Controller
@RequiredArgsConstructor
public class WebsiteController {
    private final AuthenticationService service;
    private final WebsiteService websiteService;

//    @GetMapping("/")
//    public ResponseEntity<String> sayHello() {
//        return ResponseEntity.ok("PUT HERE CODE"); // -------- !!!!!!!!! ----------
//    }

    @GetMapping("/")
    public String websites(@RequestParam(name = "domain", required = false) String domain, Model model) {
        model.addAttribute("websites", websiteService.lisOftWebsites(domain));
        return "index";
    }

    @GetMapping("/website/{id}")
    public String websiteInfo(@PathVariable Long id, Model model) {
        Website website = websiteService.getWebsiteById(id);
        model.addAttribute("website", website);
        model.addAttribute("images", website.getImages());
        return "website-info";
    }

    @PostMapping("/website/create")
    public String addWebsite(Website website, @RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2) throws IOException {
        website.setNotes("Need to check");
        website.setIp("Need to check");
        website.setStatus("Need to check");

//        if (website.getImageUrl() == null) website.setImageUrl("images/noImage.jpg");

        websiteService.saveWebsite(website, file1, file2);
        return "redirect:/";
    }

    @PostMapping("/website/remove/{id}")
    public String removeWebsite(@PathVariable Long id) {
        websiteService.removeWebsite(id);
        return "redirect:/";
    }




}
