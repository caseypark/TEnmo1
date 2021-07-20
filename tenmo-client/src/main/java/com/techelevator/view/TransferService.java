package com.techelevator.view;

import com.techelevator.tenmo.model.Accounts;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TransferService {

    private String BASE_API_URL;
    private AuthenticatedUser authenticatedUser;
    private RestTemplate restTemplate = new RestTemplate();

    public TransferService(String url, AuthenticatedUser authenticatedUser) {
        this.BASE_API_URL = url;
        this.authenticatedUser = authenticatedUser;
        this.restTemplate = restTemplate;
    }

    public void viewTransferHistory() {
        Transfer[] transferHistory;
        try {
            transferHistory = restTemplate.exchange(BASE_API_URL + "history/" + authenticatedUser.getUser().getId(),
                    HttpMethod.GET, createRequestEntity(), Transfer[].class).getBody();
            Long transferType = 0L;
            String transferTypeName = "";

            Long transferStatus = 0L;
            String transferStatusName = "";
            for(Transfer transfer : transferHistory) {
                if (transfer.getTransferType() != null) {
                    transferType = transfer.getTransferType();
                    transferTypeName = transferType == 1 ? "Request" : "Send";
                }
                if(transfer.getTransferStatus() != null) {
                    transferStatus = transfer.getTransferStatus();
                    transferStatusName = transferStatus == 1 ? "Pending" : transferStatus == 2 ? "Approved" : "Rejected";
                }
                System.out.println("----------------------------------------------");
                System.out.println("ID: " + transfer.getTransfer_id() + System.lineSeparator() + "From: " + transfer.getSenderUsername() +
                        System.lineSeparator() + "To: " + transfer.getReceiverUsername() + System.lineSeparator() + "Type: " + transferTypeName + System.lineSeparator() + "Status: " + transferStatusName + System.lineSeparator() + "Amount: " + transfer.getAmount());
            }
        } catch(RestClientResponseException ex) {
            System.out.println(ex);
        }

    }

    public void sendTeBucks() {
        Integer userId = viewUsers();
        double accounts;
        Scanner scanner = new Scanner(System.in);
        System.out.println("How much would you like to transfer? ");
        double inputAmount = scanner.nextDouble();

// + "/" + userId + "/" + inputAmount

        try {
            accounts = restTemplate.exchange(BASE_API_URL + "transfer/" + authenticatedUser.getUser().getId() + "/" + userId +
                    "/" + inputAmount,
                    HttpMethod.POST, createRequestEntity(), double.class).getBody();
            System.out.println("----------------------------------------------");
        } catch(RestClientResponseException ex) {
            System.out.println(ex);
        }

    }

    public Integer viewUsers() {
        User[] users = null;
        try {
            users = restTemplate.exchange(BASE_API_URL + "users",
                    HttpMethod.GET, createRequestEntity(), User[].class).getBody();
            System.out.println("----------------------------------------------");
            System.out.println("User ID              Name");
            for(User user : users) {
                System.out.println(user.getId() + "                 " + user.getUsername());
            }
            System.out.print(System.lineSeparator() + "Enter ID of user you are sending to (0 to cancel): ");
            Scanner scan = new Scanner(System.in);
            Integer userId = scan.nextInt();
            if(userId != 0) {
                return userId;
            }
        } catch(RestClientResponseException ex) {
            System.out.println(ex);
        }
        return null;
    }

    private HttpEntity createRequestEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authenticatedUser.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity(headers);
        return entity;
    }
}
