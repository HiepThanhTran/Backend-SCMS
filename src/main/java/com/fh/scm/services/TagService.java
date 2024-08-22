package com.fh.scm.services;

import com.fh.scm.dto.tag.TagResponse;
import com.fh.scm.pojo.Tag;

import java.util.List;
import java.util.Map;

public interface TagService {

    TagResponse getTagResponse(Tag tag);

    List<TagResponse> getAllTagResponse(Map<String, String> params);

    Tag get(Long id);

    void insert(Tag tag);

    void update(Tag tag);

    void delete(Long id);

    void softDelete(Long id);

    Long count();

    List<Tag> getAll(Map<String, String> params);
}
