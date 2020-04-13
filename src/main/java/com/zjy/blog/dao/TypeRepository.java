package com.zjy.blog.dao;

import com.zjy.blog.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<Type,Long> {
        Type findByName(String name);
}
