package com.example.shop_app.domain.service;


import com.example.shop_app.common.CommonException;
import com.example.shop_app.common.ErrorCode;
import com.example.shop_app.domain.dto.MemberDTO;
import com.example.shop_app.domain.model.Member;
import com.example.shop_app.infra.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    @Override
    public void register(MemberDTO memberDTO) {
        memberRepository.findByMemberId(memberDTO.getMemberId()).ifPresent(member -> {
            throw new CommonException(ErrorCode.MEMBER_DUPLICATION);
        });
        memberRepository.save(memberDTO.toEntity());
    }

    @Override
    public MemberDTO getMember(Long id) {
        return memberRepository.findById(id).orElseThrow().toDTO();
    }

    @Override
    public List<Member> getMemberList() {
        return memberRepository.findAll();
    }

    @Override
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);

    }

    @Override
    public void updateMember(Long memberId, MemberDTO memberDTO) {
        // memberId를 이용해 기존 회원 정보를 찾음
        Member existingMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new CommonException(ErrorCode.MEMBER_NOT_FOUND));

        // 변경된 정보를 새로운 정보로 복사
        BeanUtils.copyProperties(memberDTO, existingMember);

        // 업데이트된 회원 정보 저장
        memberRepository.save(existingMember);
    }


}
