package com.fh.scm.services.implement;

import com.fh.scm.pojo.Tag;
import com.fh.scm.repository.TagRepository;
import com.fh.scm.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TagServiceImplement implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public Tag get(Long id) {
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
    public void delete(Long id) {
        this.tagRepository.delete(id);
    }

    @Override
    public void softDelete(Long id) {
        this.tagRepository.softDelete(id);
    }

    @Override
    public Long count() {
        return this.tagRepository.count();
    }

    @Override
    public List<Tag> getAll(Map<String, String> params) {
        return this.tagRepository.getAll(params);
    }
}
