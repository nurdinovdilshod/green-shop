package com.company.payload.req;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqUserCreate {
    private String name;
    private String phoneNumber;
    private String password;
}
