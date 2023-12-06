package com.example.shop_app.presentation;

import com.example.shop_app.common.CommonException;
import com.example.shop_app.domain.dto.MemberDTO;
import com.example.shop_app.domain.model.Member;
import com.example.shop_app.domain.service.MemberService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostConstruct
    void init() {
        for (int i = 0; i < 100; i++) {
            MemberDTO memberDTO = MemberDTO.builder().memberId("user1" + i).age(10).phone(10).name("test").password("test").build();
            memberService.register(memberDTO);
        }

    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }


    @PostMapping("/register")
    public String create(MemberDTO memberDTO) {
        memberService.register(memberDTO);
        return "redirect:/list?mode=c";
    }

    @GetMapping("/saved")
    public String saved() {
        return "saved";
    }

    @GetMapping(value = {"/", "/list"})
    public String list(Model model, @RequestParam(value = "mode", required = false) String mode, @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        List<Member> memberList = memberService.getMemberList();
        model.addAttribute("mode", mode);

        model.addAttribute("page", page);
        if (memberList.size() > 10 && page == 1) {
            List<Member> updateList = memberList.stream().limit(10).toList();
            model.addAttribute("memberList", updateList);
            return "list";
        } else {
            int pageSize = 10 * page;  // 페이지 당 항목 수

            List<Member> pagedMemberList = memberList.stream()
                    .skip((long) (page - 1) * pageSize) // 첫 페이지 항목 건너뛰기
                    .limit(pageSize)                          // 이후 pageSize 개수만큼 항목 가져오기
                    .collect(Collectors.toList());            // 리스트로 변환
            model.addAttribute("memberList", pagedMemberList);
            return "list";
        }

//        model.addAttribute("memberList", memberList);
//        return "list";
    }

    @GetMapping("/member/{id}")
    public String getMember(@PathVariable(name = "id") Long id, Model model) {
        MemberDTO memberDTO = memberService.getMember(id);
        model.addAttribute("member", memberDTO);
        model.addAttribute("id", id);
        return "member";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable(name = "id") Long id, Model model) {
        MemberDTO memberDTO = memberService.getMember(id);
        model.addAttribute("id", id);
        model.addAttribute("member", memberDTO);
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String updateMember(@PathVariable(name = "id") Long id, @ModelAttribute MemberDTO memberDTO) {

        System.out.println(String.format("name: %s, phone: %d, age: %d, memberId: %s, password: %s",
                memberDTO.getName(), memberDTO.getPhone(), memberDTO.getAge(), memberDTO.getMemberId(), memberDTO.getPassword()));

        String ageStr = String.valueOf(memberDTO.getAge());
        if(ageStr.length() > 5) {
//            throw new IllegalArgumentException("age is too long");
            return  "redirect:/edit/" + id;
        }

        memberService.updateMember(id, memberDTO);
        return "redirect:/member/" + id;
    }

    @GetMapping("/delete/{id}")
    public String deleteMember(@PathVariable(name = "id") Long id) {
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
