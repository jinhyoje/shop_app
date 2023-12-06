package com.example.shop_app.common;

public enum ErrorCode {
    MEMBER_NOT_FOUND("Member not found", "멤버를 찾을 수 없습니다."),
    MEMBER_DUPLICATION("member duplication", "중복된 멤버가 있습니다.");
    // 다른 예외 유형 추가

    private final String message;
    private final String messageKorean;

    ErrorCode(String message, String messageKorean) {
        this.message = message;
        this.messageKorean = messageKorean;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageKorean() {
        return messageKorean;
    }

    }

