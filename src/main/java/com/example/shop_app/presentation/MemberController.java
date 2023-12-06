package com.example.shop_app.presentation;

import com.example.shop_app.common.CommonException;
import com.example.shop_app.domain.dto.MemberDTO;
import com.example.shop_app.domain.model.Member;
import com.example.shop_app.domain.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }


    @PostMapping("/register")
    public String create(MemberDTO memberDTO) {
        memberService.register(memberDTO);
        return "redirect:/saved";
    }

    @GetMapping("/saved")
    public String saved() {
        return "saved";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Member> memberList = memberService.getMemberList();
        model.addAttribute("memberList", memberList);
        return "list";
    }

    @GetMapping("/member/{id}")
    public String getMember(@PathVariable Long id, Model model) {
        MemberDTO memberDTO = memberService.getMember(id);
        model.addAttribute("member", memberDTO);
        model.addAttribute("id", id);
        return "member";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        MemberDTO memberDTO = memberService.getMember(id);
        model.addAttribute("id", id);
        model.addAttribute("member", memberDTO);
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String updateMember(@PathVariable Long id, @ModelAttribute MemberDTO memberDTO) {
        memberService.updateMember(id, memberDTO);
        return "redirect:/member/" + id;
    }

    @GetMapping("/delete/{id}")
    public String deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return "redirect:/deleted";
    }

    @GetMapping("/deleted")
    public String deleted() {
        return "deleted";
    }

    @ExceptionHandler(CommonException.class)
    public String handleCommonException(CommonException ex, Model model) {
        model.addAttribute("errorKorean", ex.getErrorCode().getMessageKorean());
        return "error";
    }





}
