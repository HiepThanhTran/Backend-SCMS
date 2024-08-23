package com.fh.scms.repository;

import com.fh.scms.pojo.Tag;

import java.util.List;
import java.util.Map;

public interface TagRepository {

    Tag get(Long id);

    List<Tag> getByProduct(Long productId);

    void insert(Tag tag);

    void update(Tag tag);

    void delete(Long id);

    void softDelete(Long id);

    Long count();

    List<Tag> getAll(Map<String, String> params);
}
