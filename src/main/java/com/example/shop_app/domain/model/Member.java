package com.example.shop_app.domain.model;

import com.example.shop_app.domain.dto.MemberDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data // getter, setter, toString, equals, hashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int phone;

    private int age;

    private String memberId;

    private String password;



    public MemberDTO toDTO() {
        return MemberDTO.builder()
                .name(name)
                .phone(phone)
                .age(age)
                .memberId(memberId)
                .password(password)
                .build();
    }

}
