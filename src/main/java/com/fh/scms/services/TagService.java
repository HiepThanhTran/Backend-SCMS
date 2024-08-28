package com.fh.scms.services;

import com.fh.scms.dto.tag.TagResponse;
import com.fh.scms.pojo.Tag;

import java.util.List;
import java.util.Map;

public interface TagService {

    List<Tag> findByProductId(Long productId);

    Tag findById(Long id);

    void save(Tag tag);

    void update(Tag tag);

    void delete(Long id);

    Long count();

    List<Tag> findAllWithFilter(Map<String, String> params);

    TagResponse getTagResponse(Tag tag);

    List<TagResponse> getAllTagResponse(Map<String, String> params);
}
