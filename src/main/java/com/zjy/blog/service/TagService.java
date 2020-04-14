package com.zjy.blog.service;

import com.zjy.blog.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    Tag saveTag(Tag tag);

    Tag getTag(Long id);
    //和分页功能有关
    Page<Tag> listTag(Pageable pageable);

    Tag updateTag(Long id, Tag tag);

    List<Tag> listTag();

    void deleteTag(Long id);

    Tag findByName(String name);
}
