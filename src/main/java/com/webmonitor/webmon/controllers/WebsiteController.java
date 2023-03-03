package com.webmonitor.webmon.controllers;

import com.webmonitor.webmon.models.Website;
import com.webmonitor.webmon.services.WebsiteService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;


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


    @GetMapping("/website/{domain}")
    public String websiteInfo(@PathVariable String domain, Model model) {
        Website website = websiteService.getWebsiteByDomain(domain);
        model.addAttribute("websites", websiteService.getAllWebsite());
        model.addAttribute("website", website);

        return "website-info";
    }

//    @GetMapping("/website/{id}")
//    public String websiteInfo(@RequestParam(name = "domain", required = false) String domain, @PathVariable Long id, Model model) {
//        Website website = websiteService.getWebsiteById(id);
//        model.addAttribute("websites", websiteService.lisOftWebsites(domain));
//        model.addAttribute("website", website);
//
//        return "website-info";
//    }

    @PostMapping("/website/remove/{domain}")
    public String removeWebsite(@PathVariable String domain) {
        websiteService.removeWebsite(domain);

        return "redirect:/website";
    }


//    @PostMapping("/website/remove/{id}")
//    public String removeWebsite(@PathVariable Long id) {
//        websiteService.removeWebsite(id);
//        log.info("id: " + id);
//
//        return "redirect:/website";
//    }

    @PostMapping("/website/create")
    public String addWebsite(Website website, HttpServletRequest request) throws IOException {
        buildWebsite(request, website);

        return "redirect:/website";
    }

    @PostMapping("/website/update/{domain}")
    public String updateWebsite(@PathVariable String domain, HttpServletRequest request) throws IOException {
        Website website = websiteService.getWebsiteByDomain(domain);
        buildWebsite(request, website);

        return "redirect:/website/{domain}";
    }

//    @PostMapping("/website/update/{id}")
//    public String updateWebsite(@PathVariable Long id) throws IOException {
//        Website website = websiteService.getWebsiteById(id);
//        buildWebsite(website);
//
//        return "redirect:/website/{id}";
//    }


//    @PostMapping("/website/update-local/{id}")
//    public String updateWebsites(@RequestParam(name = "domain", required = false) String domain, @PathVariable Long id) throws IOException {
//        Website website = websiteService.getWebsiteById(id);
//        buildWebsite(website);
//
//        return "redirect:/website";
//    }

    @PostMapping("/website/update-local/{domain}")
    public String updateWebsites(@PathVariable String domain, Model model, HttpServletRequest request) throws IOException {
        Website website = websiteService.getWebsiteByDomain(domain);
        buildWebsite(request, website);

        return "redirect:/website";
    }

    public String checkUrl(String domain) {
        if (domain.startsWith("https://")) return domain.replace("https://", "");
        else if (domain.startsWith("http://")) return domain.replace("http://", "");
        return domain.trim();
    }

    public void buildWebsite(HttpServletRequest request, Website website) throws IOException {

        website.setDomain(checkUrl(website.getDomain()));

        String status = websiteService.checkOnline(website.getDomain());

        if (!status.equals("online")) {
            website.setStatus("unavailable");
            website.setIp("No data");
            website.setLocation("No data");
            website.setDelayResponse("No data");
            website.setLoadTime("No data");
            website.setLastUpdate(websiteService.getCurrentDateTimeAsString());
            website.setScreenshot(websiteService.imageToBase64());
            websiteService.saveWebsite(request, website);
        } else {

            website.setStatus(status);
            String[] loadTimeAndScreenshot = websiteService.checkLoadTimeAndScreenshot(website.getDomain());
            String[] ipAndLocation = websiteService.checkIp(website.getDomain());

            website.setIp(ipAndLocation[0]);
            website.setLocation(ipAndLocation[1]);
            website.setDelayResponse(websiteService.checkDelay(website.getDomain()));
            website.setLoadTime(loadTimeAndScreenshot[0]);
            website.setScreenshot(loadTimeAndScreenshot[1]);
            website.setLastUpdate(websiteService.getCurrentDateTimeAsString());
            websiteService.saveWebsite(request, website);
        }

    }


}
