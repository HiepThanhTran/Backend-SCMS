package com.fh.scms.services.implement;

import com.fh.scms.dto.tag.TagResponse;
import com.fh.scms.pojo.Tag;
import com.fh.scms.repository.TagRepository;
import com.fh.scms.services.TagService;
import org.jetbrains.annotations.NotNull;
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
    public List<Tag> findByProductId(Long productId) {
        return this.tagRepository.findByProductId(productId);
    }

    @Override
    public Tag findById(Long id) {
        return this.tagRepository.findById(id);
    }

    @Override
    public void save(Tag tag) {
        this.tagRepository.save(tag);
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
    public Long count() {
        return this.tagRepository.count();
    }

    @Override
    public List<Tag> findAllWithFilter(Map<String, String> params) {
        return this.tagRepository.findAllWithFilter(params);
    }

    @Override
    public TagResponse getTagResponse(@NotNull Tag tag) {
        return TagResponse.builder()
                .id(tag.getId())
                .name(tag.getName())
                .description(tag.getDescription())
                .build();
    }

    @Override
    public List<TagResponse> getAllTagResponse(Map<String, String> params) {
        return this.tagRepository.findAllWithFilter(params).parallelStream()
                .map(this::getTagResponse)
                .collect(java.util.stream.Collectors.toList());
    }
}
