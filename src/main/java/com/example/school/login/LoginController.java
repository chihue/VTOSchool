package com.example.school.login;

import com.example.school.login.model.Login;
import com.example.school.login.model.LoginResponse;
import com.example.school.model.ApiResponse;
import org.springframework.validation.Errors;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    /**
     * It takes a Login object, validates it, and returns a response with a token if the login is successful
     *
     * @param login The login object that contains the username and password.
     * @param errors This is the object that contains the validation errors.
     * @return A ResponseEntity object is being returned.
     */
    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody Login login, Errors errors) {
        ApiResponse apiResponse = new ApiResponse();

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                apiResponse.addError(error.getDefaultMessage());
            }
        }   else {
            LoginResponse loginResponse = LoginService.authUser(login);

            String token = loginResponse.getToken();
            apiResponse.setToken(token);
            apiResponse.setStatus(loginResponse.getStatus());
            apiResponse.setValidTime(loginResponse.getValidTime());
        }

        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }
}
