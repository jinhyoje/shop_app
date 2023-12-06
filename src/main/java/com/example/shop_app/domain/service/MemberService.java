package com.example.shop_app.domain.service;

import com.example.shop_app.domain.dto.MemberDTO;
import com.example.shop_app.domain.model.Member;

import java.util.List;

public interface MemberService {
    void register(MemberDTO memberDTO);
    MemberDTO getMember(Long id);
    List<Member> getMemberList();
    void deleteMember(Long id);
    void updateMember(Long id, MemberDTO memberDTO);

}
