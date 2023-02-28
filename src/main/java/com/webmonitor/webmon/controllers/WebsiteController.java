package com.webmonitor.webmon.controllers;

import com.webmonitor.webmon.models.Website;
import com.webmonitor.webmon.services.WebsiteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping
public class WebsiteController {
    private final WebsiteService websiteService;

    @GetMapping("/website")
    public String websites(@RequestParam(name = "domain", required = false) String domain, Model model) {
        model.addAttribute("websites", websiteService.lisOftWebsites(domain));
        return "website";
    }


    @GetMapping("/website/{id}")
    public String websiteInfo(@RequestParam(name = "domain", required = false) String domain, @PathVariable Long id, Model model) {
        Website website = websiteService.getWebsiteById(id);
        model.addAttribute("websites", websiteService.lisOftWebsites(domain));
        model.addAttribute("website", website);
        log.info("Domain: " + domain, "Id: " + id);
        return "website-info";
    }

    @PostMapping("/website/create")
    public String addWebsite(Website website) throws IOException {
        website.setStatus("No info");
        website.setIp("No info");
        website.setNotes("No info");
        website.setDelayResponse("No info");
        websiteService.saveWebsite(website);
        return "redirect:/website";
    }


    @PostMapping("/website/remove/{id}")
    public String removeWebsite(@PathVariable Long id) {
        websiteService.removeWebsite(id);
        log.info("id: " + id);
        return "redirect:/website";
    }



}
