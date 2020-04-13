package com.zjy.blog.service;

import com.zjy.blog.dao.TypeRepository;
import com.zjy.blog.entity.Type;
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
public class TypeServiceImpl implements TypeService {

    @Autowired
    TypeRepository typeRepository;
    @Transactional
    @Override
    public Type saveType(Type type) {
        Type save = typeRepository.save(type);
        return save;
    }

    @Override
    @Transactional
    public Type getType(Long id) {
        Optional<Type> res = typeRepository.findById(id);
        return res.get();
    }

    @Override
    @Transactional
    public Page<Type> listType(Pageable pageable) {
        Page<Type> all = typeRepository.findAll(pageable);
        return all;
    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Override
    @Transactional
    public Type updateType(Long id, Type type) {
        Optional<Type> res = typeRepository.findById(id);
        Type t = res.get();
        if(t == null){
            throw new NotFoundException("该类别不存在");
        }
//      把type中的数据复制给res
      //  BeanUtils.copyProperties(type,t);
        Type save = typeRepository.save(type);
        return save;
    }

    @Override
    @Transactional
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Type findByName(String name) {
        return typeRepository.findByName(name);
    }
}
