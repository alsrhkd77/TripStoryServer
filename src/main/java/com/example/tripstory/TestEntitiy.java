package com.example.tripstory;


import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "test")
public class TestEntitiy {

    @Id
    private Integer id;

    private Integer pw;

    @Builder
    TestEntitiy(int id, int pw){
        this.id = id;
        this.pw = pw;
    }
}
