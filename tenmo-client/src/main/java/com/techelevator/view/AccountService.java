package com.techelevator.view;


import com.techelevator.tenmo.model.Accounts;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import org.springframework.http.*;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

public class AccountService {

    private String BASE_API_URL;
    private AuthenticatedUser authenticatedUser;
    private RestTemplate restTemplate = new RestTemplate();

    public AccountService(String url, AuthenticatedUser authenticatedUser) {
        this.BASE_API_URL = url;
        this.authenticatedUser = authenticatedUser;
        this.restTemplate = restTemplate;
    }

    public double viewBalance() {
        Double balance = null;
        try {
            balance = restTemplate.exchange(BASE_API_URL + "balance/" + authenticatedUser.getUser().getId(), HttpMethod.GET, createRequestEntity(), Double.class).getBody();
            System.out.println("Your current account balance is: $" + balance);
        } catch(RestClientResponseException ex) {
            System.out.println(ex);
        }
        return balance;
    }

    private HttpEntity createRequestEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity entity = new HttpEntity(headers);
        return entity;
    }
}
