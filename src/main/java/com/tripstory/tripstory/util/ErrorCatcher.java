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

    /**
     * 해당 파일 타입이 사용가능한 타입인지 확인함
     * @param contentType
     * @return 사용가능하면 true, 불가능하면 false 반환
     */
    public static boolean isUsableImageContentType(String contentType) {
        if(contentType.split("/")[0] != "image") {
            return false;
        }
        switch (contentType.split("/")[1]) {
            case "jpg":
            case "png":
            case "gif":
                return true;
            default:
                return false;
        }
    }
}
