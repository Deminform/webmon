package com.webmonitor.webmon.repositories;

import com.webmonitor.webmon.models.User;
import com.webmonitor.webmon.models.Website;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface WebsiteRepository extends JpaRepository<Website, Long> {
    List<Website> findAllByUser(User user);
    Optional<Website> findByDomainAndUser(String domain, User user);

}
