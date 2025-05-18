package com.management.shopfashion.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public enum ErrorCode {
	USER_NOT_EXITED(1000,"User not Exit"),
	ACCOUNT_NOT_ACTIVE (1001,"Account not Active"),
	PASSWORD_NOT_MATCH(1002,"Password not Match")
    ;
	ErrorCode(int code, String message ) {
        this.code = code;
        this.message = message;
    }

    private final int code;
    private final String message;
}
