package com.fh.scms.services;

import com.fh.scms.dto.tag.TagResponse;
import com.fh.scms.pojo.Tag;

import java.util.List;
import java.util.Map;

public interface TagService {

    TagResponse getTagResponse(Tag tag);

    List<TagResponse> getAllTagResponse(Map<String, String> params);

    Tag get(Long id);

    List<Tag> getByProduct(Long productId);

    void insert(Tag tag);

    void update(Tag tag);

    void delete(Long id);

    void softDelete(Long id);

    Long count();

    List<Tag> getAll(Map<String, String> params);
}
