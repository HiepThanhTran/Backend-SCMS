package com.fh.scm.services;

import com.fh.scm.pojo.Tag;

import java.util.List;
import java.util.Map;

public interface TagService {

    Tag get(Long id);

    void insert(Tag tag);

    void update(Tag tag);

    void delete(Long id);

    void softDelete(Long id);

    Long count();

    List<Tag> getAll(Map<String, String> params);
}
