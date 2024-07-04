package com.salman.book_network.service;

import com.salman.book_network.dto.request.AuthenticateRequest;
import com.salman.book_network.dto.response.AuthenticateResponse;
import com.salman.book_network.dto.request.RegistrationRequest;
import com.salman.book_network.entity.Token;
import com.salman.book_network.entity.User;
import com.salman.book_network.repo.RoleRepository;
import com.salman.book_network.repo.TokenRepository;
import com.salman.book_network.repo.UserRepository;
import com.salman.book_network.security.JwtService;
import com.salman.book_network.util.email.EmailService;

import com.salman.book_network.util.email.EmailTemplateName;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;
    private final EmailService emailService;

    private final AuthenticationManager authenticationManager;

    @Value("${application.mailing.mailing.activation-url}")
   private String activationUrl;

    private final JwtService jwtService;
    public void register(RegistrationRequest request) throws MessagingException {

        var userRole = roleRepository.findByName("USER").orElseThrow(() -> new IllegalStateException("Role Use was noy initialize"));

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enable(false)
                .roles(List.of(userRole))
                .build();

        userRepository.save(user);
       sendEmailValidation(user);

    }

    private void sendEmailValidation(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);

        emailService.senEmail(user.getEmail()
                , user.fullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account activation"

        );
    }

    private String generateAndSaveActivationToken(User user) {
        //generate token;
        String generateToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generateToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generateToken;
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }

    public AuthenticateResponse authenticate(AuthenticateRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var claims = new HashMap<String, Object>();
        var user = ((User) auth.getPrincipal());
        claims.put("fullName", user.fullName());

        var jwtToken = jwtService.generateToken(claims, (User) auth.getPrincipal());
        return AuthenticateResponse.builder()
                .token(jwtToken)
                .build();
    }
    @Transactional
    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                // todo exception has to be defined
                .orElseThrow(() -> new RuntimeException("Invalid token"));
        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendEmailValidation(savedToken.getUser());
            throw new RuntimeException("Activation token has expired. A new token has been send to the same email address");
        }

        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEnable(true);
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }


}
