package com.tripstory.tripstory.util;

import org.springframework.validation.BindingResult;

public class ErrorCatcher {
    /**
     * 바인딩 에러를 하나의 문자열로 만들어 반환
     * @param bindingResult
     * @return
     */
    public static String getBindingError(BindingResult bindingResult) {
        StringBuilder sb = new StringBuilder();
        bindingResult.getAllErrors().forEach(
                error -> {
                    sb.append(error.getDefaultMessage());
                    sb.append("\n");
                });
        return sb.toString();
    }

    public static boolean isUsableImageContentType(String contentType) {

        return true;
    }
}
