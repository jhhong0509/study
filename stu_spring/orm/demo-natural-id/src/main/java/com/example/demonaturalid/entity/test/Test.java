package com.example.demonaturalid.entity.test;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import javax.persistence.*;

@Cacheable
@NaturalIdCache
@Entity
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Column(name = "test11")
    private String unique;

    private String content;

    public Test(Long id, String unique, String content) {
        this.id = id;
        this.unique = unique;
        this.content = content;
    }

    public Test() {
    }

    public Long getId() {
        return id;
    }

    public String getUnique() {
        return unique;
    }

    public String getContent() {
        return content;
    }
}
