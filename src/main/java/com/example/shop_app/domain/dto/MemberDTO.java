package com.example.shop_app.domain.dto;

import com.example.shop_app.domain.model.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {

    private String name;
    private int phone;
    private int age;
    private String memberId;
    private String password;

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .phone(phone)
                .age(age)
                .memberId(memberId)
                .password(password)
                .build();
    }
}
