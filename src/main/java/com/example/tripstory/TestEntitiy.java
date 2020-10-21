package com.example.tripstory;


import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "test")
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
