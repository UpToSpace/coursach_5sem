package com.example.javaproject.controllers;


import com.example.javaproject.dto.AuthResponse;
import com.example.javaproject.forms.LoginForm;
import com.example.javaproject.forms.RegistrationForm;
import com.example.javaproject.jwt.JWTProvider;
import com.example.javaproject.models.User;
import com.example.javaproject.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Slf4j
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    private final PasswordEncoder passwordEncoder;
    private final JWTProvider jwtProvider;

    //    -------------LOGIN + REGISTRATION-------------

    private final UserService userService;

    @Autowired
    public UserController(PasswordEncoder passwordEncoder, JWTProvider jwtProvider, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.userService = userService;
    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<User>> allUsersPage() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @DeleteMapping("/admin/users/delete/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "email") String email) {
        userService.deleteUser(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser (@RequestBody LoginForm loginForm) {
        String email = loginForm.getEmail();
        String password = loginForm.getPassword();

        User user = userService.getUser(email, password);

        String accessToken = jwtProvider.generateToken(user);

        AuthResponse response = new AuthResponse(accessToken, user.getRole());
        //response.setHeader("access_token", access_token);
        //response.setHeader("refresh_token", refresh_token);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/registration")
    ResponseEntity registerUser (@RequestBody RegistrationForm registrationForm) {
        String email = registrationForm.getEmail();
        String username = registrationForm.getUsername();
        String password = registrationForm.getPassword();
        //String password = passwordEncoder.encode(registrationForm.getPassword());
        userService.registerUser(email, username, password);
        return ResponseEntity.ok().body(HttpStatus.OK);
    }

    @GetMapping("/user/profile")
    ResponseEntity<List<User>> getUser(@RequestHeader(name = "Authorization") String header) {
        String token = header.substring("Bearer ".length());
        String email = jwtProvider.getEmailFromToken(token);
        User user = userService.getUser(email);
        return ResponseEntity.ok().body(Collections.singletonList(user));
    }

    @PostMapping("/user/updateprofile")
    ResponseEntity updateUser(@RequestBody RegistrationForm registrationForm,
                              @RequestHeader(name = "Authorization") String header) {
        String token = header.substring("Bearer ".length());
        String email = jwtProvider.getEmailFromToken(token);
        userService.updateUser(email, registrationForm.getUsername(), registrationForm.getPassword());
        return ResponseEntity.ok().body(HttpStatus.OK);
    }

//@GetMapping("/token/refresh")
//public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
//    String authorizationHeader = request.getHeader(AUTHORIZATION);
//    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//        try {
//            String refresh_token = authorizationHeader.substring("Bearer ".length());
//            Algorithm algorithm = Algorithm.HMAC256("javathebest".getBytes());
//            JWTVerifier verify = JWT.require(algorithm).build();
//            DecodedJWT decodedJWT = verify.verify(refresh_token);
//            String username = decodedJWT.getSubject();
//            User user = userService.getUser(username);
//            String access_token = JWT.create()
//                    .withSubject(user.getUsername())
//                    .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
//                    .withIssuer(request.getRequestURI())
//                    .withClaim("roles", user.getRole().getRoleName())
//                    .sign(algorithm);
//            Map<String, String> tokens = new HashMap<>();
//            tokens.put("access_token", access_token);
//            tokens.put("refresh_token", refresh_token);
//            response.setContentType(APPLICATION_JSON_VALUE);
//            new ObjectMapper().writeValue(response.getOutputStream(), tokens);
//        } catch (Exception exception) {
//            log.error("Error : {}", exception.getMessage());
//            response.setStatus(FORBIDDEN.value());
//            Map<String, String> errors = new HashMap<>();
//            errors.put("error_message", exception.getMessage());
//            response.setContentType(APPLICATION_JSON_VALUE);
//            new ObjectMapper().writeValue(response.getOutputStream(), errors);
//        }
//    } else {
//        throw new RuntimeException("Refresh token is missing");
//    }
//}
}
