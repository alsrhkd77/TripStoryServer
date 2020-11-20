package com.tripstory.tripstory.util;

public interface PasswordEncoder {

    String encode(String origin, String key);
}
