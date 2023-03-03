package com.webmonitor.webmon.repositories;

import com.webmonitor.webmon.models.Website;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WebsiteRepository extends JpaRepository<Website, Long> {
    Website findByDomain(String domain);
}
