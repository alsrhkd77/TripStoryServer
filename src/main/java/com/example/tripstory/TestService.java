package com.example.tripstory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {
    @Autowired
    TestRepository testRepository;

    public List<TestEntitiy> findAll(){
        List<TestEntitiy> tests = new ArrayList<>();
        testRepository.findAll().forEach(e -> tests.add(e));
        return tests;
    }

    public TestEntitiy save(TestEntitiy testEntitiy){
        testRepository.save(testEntitiy);
        return testEntitiy;
    }
}
