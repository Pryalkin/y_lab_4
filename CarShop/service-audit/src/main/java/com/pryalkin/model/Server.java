package com.pryalkin.model;

import lombok.Data;

@Data
public class Server {

    private Long id;
    private String servername;
    private String serverpassword;
    private String token;

}
