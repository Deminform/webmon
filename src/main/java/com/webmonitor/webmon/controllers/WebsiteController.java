package com.webmonitor.webmon.controllers;

import com.webmonitor.webmon.models.Website;
import com.webmonitor.webmon.services.WebsiteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping
public class WebsiteController {
    private final WebsiteService websiteService;

    @GetMapping("/website")
    public String websites(@RequestParam(name = "domain", required = false) String domain, Model model) {
        List<Website> websiteList = new ArrayList<>();
        Website website = null;

//        for (Website list : websiteService.lisOftWebsites(domain)) {
//            website = list;
//            website.setStatus(websiteService.checkOnline(website.getDomain()));
//            website.setIp(websiteService.checkIp(website.getDomain()));
//            website.setDelayResponse(websiteService.checkDelay(website.getDomain()));
//            website.setLoadTime(websiteService.checkLoadTimeAndScreenshot(website.getDomain())[0]);
//            website.setScreenshot(websiteService.checkLoadTimeAndScreenshot(website.getDomain())[1]);
//
//            websiteList.add(website);
//        }

        log.info("++++++++++++++++++++++++++++++++++++ WebsiteList is: ");

        model.addAttribute("websites", websiteService.lisOftWebsites(domain));

        return "website";
    }


    @GetMapping("/website/{id}")
    public String websiteInfo(@RequestParam(name = "domain", required = false) String domain, @PathVariable Long id, Model model) {
        Website website = websiteService.getWebsiteById(id);
        model.addAttribute("websites", websiteService.lisOftWebsites(domain));
        model.addAttribute("website", website);

        return "website-info";
    }

    @PostMapping("/website/create")
    public String addWebsite(Website website) throws IOException {

        String status = websiteService.checkOnline(website.getDomain());

        if (!status.equals("Online")) {
            website.setStatus("Offline (Domain is correct?)");
            website.setIp("No data");
            website.setLocation("No data");
            website.setDelayResponse("No data");
            website.setLoadTime("No data");
            website.setScreenshot(websiteService.imageToBase64());
            websiteService.saveWebsite(website);
        } else {
            website.setStatus(status);
            String[] loadTimeAndScreenshot = websiteService.checkLoadTimeAndScreenshot(website.getDomain());
            String[] ipAndLocation = websiteService.checkIp(website.getDomain());

            website.setIp(ipAndLocation[0]);
            website.setLocation(ipAndLocation[1]);
            website.setDelayResponse(websiteService.checkDelay(website.getDomain()));
            website.setLoadTime(loadTimeAndScreenshot[0]);
            website.setScreenshot(loadTimeAndScreenshot[1]);
            websiteService.saveWebsite(website);
        }

        return "redirect:/website";
    }


    @PostMapping("/website/remove/{id}")
    public String removeWebsite(@PathVariable Long id) {
        websiteService.removeWebsite(id);
        log.info("id: " + id);
        return "redirect:/website";
    }

    @PostMapping("/website/update/{id}")
    public String updateWebsite(@PathVariable Long id) throws IOException {
        Website website = websiteService.getWebsiteById(id);
        String status = websiteService.checkOnline(website.getDomain());

        website.setStatus(status);
        String[] loadTimeAndScreenshot = websiteService.checkLoadTimeAndScreenshot(website.getDomain());
        String[] ipAndLocation = websiteService.checkIp(website.getDomain());

        website.setIp(ipAndLocation[0]);
        website.setLocation(ipAndLocation[1]);
        website.setDelayResponse(websiteService.checkDelay(website.getDomain()));
        website.setLoadTime(loadTimeAndScreenshot[0]);
        website.setScreenshot(loadTimeAndScreenshot[1]);
        websiteService.saveWebsite(website);

        log.info("Update website info by id: " + id);
        return "redirect:/website/{id}";
    }

    @PostMapping("/website/update-local/{id}")
    public String updateWebsites(@RequestParam(name = "domain", required = false) String domain, @PathVariable Long id) throws IOException {
        Website website = websiteService.getWebsiteById(id);
        String status = websiteService.checkOnline(website.getDomain());

        if (!status.equals("Online")) {
            website.setStatus("Offline (Domain is correct?)");
            website.setIp("No data");
            website.setLocation("No data");
            website.setDelayResponse("No data");
            website.setLoadTime("No data");
            website.setScreenshot(websiteService.imageToBase64());
            websiteService.saveWebsite(website);
        } else {
            website.setStatus(status);
            String[] loadTimeAndScreenshot = websiteService.checkLoadTimeAndScreenshot(website.getDomain());
            String[] ipAndLocation = websiteService.checkIp(website.getDomain());

            website.setIp(ipAndLocation[0]);
            website.setLocation(ipAndLocation[1]);
            website.setDelayResponse(websiteService.checkDelay(website.getDomain()));
            website.setLoadTime(loadTimeAndScreenshot[0]);
            website.setScreenshot(loadTimeAndScreenshot[1]);
            websiteService.saveWebsite(website);
        }
        return "redirect:/website";
    }






}
