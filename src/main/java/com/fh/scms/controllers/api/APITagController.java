package com.fh.scms.controllers.api;

import com.fh.scms.dto.tag.TagResponse;
import com.fh.scms.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/tags", produces = "application/json; charset=UTF-8")
public class APITagController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity<?> list(@RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        List<TagResponse> tags = this.tagService.getAllTagResponse(params);

        return ResponseEntity.ok(tags);
    }
}
