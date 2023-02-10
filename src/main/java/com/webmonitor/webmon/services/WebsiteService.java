package com.webmonitor.webmon.services;


import com.webmonitor.webmon.models.Image;
import com.webmonitor.webmon.models.Website;
import com.webmonitor.webmon.repositories.WebsiteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class WebsiteService {

    private final WebsiteRepository websiteRepository;

    public List<Website> lisOftWebsites(String domain) {
        if (domain != null) return websiteRepository.findByDomain(domain);
        return websiteRepository.findAll();
    }

//    public void saveWebsite(Website website)  {
//        log.info("Saving new Website. Domain: {}", website.getDomain());
//        websiteRepository.save(website);
//    }


    public void removeWebsite(Long id) {
        websiteRepository.deleteById(id);
    }


    public Website getWebsiteById(Long id) {
        return websiteRepository.findById(id).orElse(null);
    }


    public void saveWebsite(Website website, MultipartFile file1, MultipartFile file2) throws IOException {
        Image preview;
        Image logo;
        if (file1.getSize() != 0) {
            preview = toImageEntity(file1);
            preview.setPreviewImage(true);
            website.addImageToWebsite(preview);
            preview.setOriginalFileName(website.getDomain()+ "_preview");
        }

        if (file2.getSize() != 0) {
            logo = toImageEntity(file2);
            website.addImageToWebsite(logo);
            logo.setOriginalFileName(website.getDomain()+ "_logo" );
        }

        log.info("Saving new Website. Domain: {}; Status: {}", website.getDomain(), website.getStatus());
        Website websiteFromDb = websiteRepository.save(website);
        websiteFromDb.setPreviewImageId(websiteFromDb.getImages().get(0).getId());
        websiteRepository.save(website);
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }

}