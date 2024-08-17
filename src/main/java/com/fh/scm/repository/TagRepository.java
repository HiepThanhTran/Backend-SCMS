package com.fh.scm.repository;

import com.fh.scm.pojo.Tag;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface TagRepository {

    Tag get(UUID id);

    void insert(Tag tag);

    void update(Tag tag);

    void delete(UUID id);

    void softDelete(UUID id);

    void insertOrUpdate(Tag tag);

    Long count();

    Boolean exists(UUID id);

    List<Tag> getAll(Map<String, String> params);
}
