package com.zjy.blog.service;

import com.zjy.blog.dao.TagRepository;
import com.zjy.blog.entity.Tag;
import com.zjy.blog.exception.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagRepository tagRepository;

    @Override
    @Transactional
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    @Transactional
    public Tag getTag(Long id) {
        Optional<Tag> tag = tagRepository.findById(id);
        return tag.get();
    }

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        Page<Tag> all = tagRepository.findAll(pageable);
        return all;
    }

    @Override
    public Tag updateTag(Long id, Tag tag) {
        Optional<Tag> tag1 = tagRepository.findById(id);
        Tag t = tag1.get();
        if(t == null){
            throw new NotFoundException("标签不存在！");
        }
        //BeanUtils.copyProperties(tag,t);
        Tag save = tagRepository.save(tag);
        return save;
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    @Override
    public Tag findByName(String name) {
        return tagRepository.findByName(name);
    }
}
