package com.fh.scms.services.implement;

import com.fh.scms.dto.tag.TagResponse;
import com.fh.scms.pojo.Tag;
import com.fh.scms.repository.TagRepository;
import com.fh.scms.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TagServiceImplement implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public TagResponse getTagResponse(Tag tag) {
        return TagResponse.builder()
                .id(tag.getId())
                .name(tag.getName())
                .description(tag.getDescription())
                .build();
    }

    @Override
    public List<TagResponse> getAllTagResponse(Map<String, String> params) {
        return this.tagRepository.getAll(params).stream()
                .map(this::getTagResponse)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public Tag get(Long id) {
        return this.tagRepository.get(id);
    }

    @Override
    public List<Tag> getByProduct(Long productId) {
        return this.tagRepository.getByProduct(productId);
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
