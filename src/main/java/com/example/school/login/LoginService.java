package com.example.school.login;

import com.example.school.login.model.Login;
import com.example.school.login.model.LoginResponse;
import com.example.school.teacher.TeacherService;
import com.example.school.teacher.model.Teacher;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoginService {
    private static final Logger log = LogManager.getLogger(LoginService.class);

    @Value("${validTime}")
    private Long sValidTime;
    private static Long validTime;

    @Value("${secretJWT}")
    private String sSecretJWT;
    private static String secretJWT;

    @Value("${idJWT}")
    private String sIdJWT;
    private static String idJWT;

    public static final String HEADER = "Authorization";
    public static final String PREFIX = "Bearer ";



    public static LoginResponse authUser(Login login) {
        LoginResponse loginResponse = new LoginResponse();
        Teacher teacher = TeacherService.getTeacherByName(login.getUsername());

        if (teacher != null) {
            if (BCrypt.checkpw(login.getPassword(), teacher.getPassword())) {
                loginResponse = LoginService.getToken(teacher);
            }   else {
                loginResponse.setMessage("Password and user not match");
                loginResponse.setStatus(HttpStatus.BAD_REQUEST);
                log.error("Password is incorrect");
            }
        }   else {
            loginResponse.setMessage("Password and user not match");
            loginResponse.setStatus(HttpStatus.BAD_REQUEST);
            log.error("User not found");
        }

        return loginResponse;
    }

    private static LoginResponse getToken(Teacher teacher) {
        LoginResponse response = new LoginResponse();

        try {
            List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("TEACHER");

            Date now = new Date();
            Date validTo = new Date(now.getTime() + LoginService.validTime);


            String token = Jwts
                    .builder()
                    .setId(LoginService.idJWT)
                    .setSubject(teacher.getUsername())
                    .claim("authorities",
                            grantedAuthorities.stream()
                                    .map(GrantedAuthority::getAuthority)
                                    .collect(Collectors.toList()))
                    .setIssuedAt(now)
                    .setExpiration(validTo)
                    .signWith(SignatureAlgorithm.HS512, LoginService.secretJWT.getBytes()).compact();
            token = "Bearer " + token;

            response.setToken(token);
            response.setValidTime(validTo.getTime());
            response.setStatus(HttpStatus.OK);
        }   catch (Exception e) {
            log.error("Error while generating token");
            response.setMessage("Internal server error");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @PostConstruct
    public void init() {
        validTime = sValidTime;
        secretJWT = sSecretJWT;
        idJWT = sIdJWT;
    }

    public static Long getValidTime() {
        return validTime;
    }

    public static String getSecretJWT() {
        return secretJWT;
    }

    public static String getIdJWT() {
        return idJWT;
    }
}
