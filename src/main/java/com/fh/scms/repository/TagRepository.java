package com.fh.scms.repository;

import com.fh.scms.pojo.Tag;

import java.util.List;
import java.util.Map;

public interface TagRepository {

    List<Tag> findByProductId(Long productId);

    Tag findById(Long id);

    void save(Tag tag);

    void update(Tag tag);

    void delete(Long id);

    Long count();

    List<Tag> findAllWithFilter(Map<String, String> params);
}
