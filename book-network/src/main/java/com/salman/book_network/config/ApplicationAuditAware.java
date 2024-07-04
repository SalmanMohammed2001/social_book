package com.salman.book_network.config;

import com.salman.book_network.entity.User;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.boot.autoconfigure.pulsar.PulsarProperties;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

import java.util.Optional;

public class ApplicationAuditAware implements AuditorAware<Integer> {
    @Override
    public Optional<Integer> getCurrentAuditor() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken){
            return Optional.empty();
        }
        User userPrinciple= (User) authentication.getPrincipal();
        return Optional.ofNullable(userPrinciple.getId());
    }
}
