package com.fh.scm.repository;

import com.fh.scm.pojo.Tag;

import java.util.List;
import java.util.Map;

public interface TagRepository {

    Tag get(Long id);

    void insert(Tag tag);

    void update(Tag tag);

    void delete(Long id);

    void softDelete(Long id);

    void insertOrUpdate(Tag tag);

    Long count();

    List<Tag> getAll(Map<String, String> params);
}
