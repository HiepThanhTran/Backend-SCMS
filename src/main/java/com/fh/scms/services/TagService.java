package com.fh.scms.services;

import com.fh.scms.dto.tag.TagResponse;
import com.fh.scms.pojo.Tag;

import java.util.List;
import java.util.Map;

public interface TagService {

    TagResponse getTagResponse(Tag tag);

    List<TagResponse> getAllTagResponse(Map<String, String> params);

    Tag findById(Long id);

    List<Tag> findByProductId(Long productId);

    void save(Tag tag);

    void update(Tag tag);

    void delete(Long id);

    Long count();

    List<Tag> findAllWithFilter(Map<String, String> params);
}
