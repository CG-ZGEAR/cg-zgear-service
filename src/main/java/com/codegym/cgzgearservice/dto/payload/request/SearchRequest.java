package com.codegym.cgzgearservice.dto.payload.request;

import lombok.Data;

/**
 * @author ADMIN
 */
@Data
public class SearchRequest {

    private String username;
    private String fullName;
    private String email;

}
