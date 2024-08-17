package com.fh.scm.services.implement;

import com.fh.scm.pojo.Tag;
import com.fh.scm.repository.TagRepository;
import com.fh.scm.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class TagServiceImplement implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public Tag get(UUID id) {
        return this.tagRepository.get(id);
    }

    @Override
    public void insert(Tag tag) {
        this.tagRepository.insert(tag);
    }

    @Override
    public void update(Tag tag) {
        this.tagRepository.update(tag);
    }

    @Override
    public void delete(UUID id) {
        this.tagRepository.delete(id);
    }

    @Override
    public void softDelete(UUID id) {
        this.tagRepository.softDelete(id);
    }

    @Override
    public void insertOrUpdate(Tag tag) {
        this.tagRepository.insertOrUpdate(tag);
    }

    @Override
    public Long count() {
        return this.tagRepository.count();
    }

    @Override
    public Boolean exists(UUID id) {
        return this.tagRepository.exists(id);
    }

    @Override
    public List<Tag> getAll(Map<String, String> params) {
        return this.tagRepository.getAll(params);
    }
}
