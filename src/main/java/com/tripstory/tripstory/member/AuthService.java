package com.tripstory.tripstory.member;

public interface AuthService {

    boolean isIdDuplicate(String id);

    String join(String id, String password, String name, String email);

    boolean loginByTS(String id, String password);

    boolean loginBySocial(String id);
}
