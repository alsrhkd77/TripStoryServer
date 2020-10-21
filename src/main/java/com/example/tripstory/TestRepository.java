package com.example.tripstory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<TestEntitiy, Integer> {
    public List<TestEntitiy> findById(int id);
    public List<TestEntitiy> findByPw(int pw);
}
