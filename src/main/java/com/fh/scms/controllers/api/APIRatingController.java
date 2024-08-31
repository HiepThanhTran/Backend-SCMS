package com.fh.scms.controllers.api;

import com.fh.scms.dto.MessageResponse;
import com.fh.scms.dto.rating.RatingRequestUpdate;
import com.fh.scms.dto.rating.RatingResponse;
import com.fh.scms.pojo.Rating;
import com.fh.scms.pojo.User;
import com.fh.scms.services.RatingService;
import com.fh.scms.services.UserService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/ratings", produces = "application/json; charset=UTF-8")
public class APIRatingController {

    private final RatingService ratingService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> listRatings(@RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        List<RatingResponse> ratingList = this.ratingService.getAllRatingResponse(params);

        return ResponseEntity.ok(ratingList);
    }

    @GetMapping(path = "/{ratingId}")
    public ResponseEntity<?> getRating(@PathVariable(value = "ratingId") Long id) {
        Rating rating = this.ratingService.findById(id);
        Optional.ofNullable(rating).orElseThrow(() -> new EntityNotFoundException("Không tìm thấy đánh giá"));

        RatingResponse ratingResponse = this.ratingService.getRatingResponse(rating);

        return ResponseEntity.ok(ratingResponse);
    }

    @PostMapping(path = "/{ratingId}")
    public ResponseEntity<?> updateRating(Principal principal, @PathVariable(value = "ratingId") Long id,
                                          @ModelAttribute @Valid RatingRequestUpdate ratingRequestUpdate, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(MessageResponse.fromBindingResult(bindingResult));
        }

        Rating rating = this.ratingService.findById(id);
        Optional.ofNullable(rating).orElseThrow(() -> new EntityNotFoundException("Không tìm thấy đánh giá"));

        User user = this.userService.findByUsername(principal.getName());
        if (!Objects.equals(user.getId(), rating.getUser().getId())) {
            throw new AccessDeniedException("Bạn không có quyền cập nhật đánh giá này");
        }

        rating = this.ratingService.update(rating, ratingRequestUpdate);

        return ResponseEntity.ok(this.ratingService.getRatingResponse(rating));
    }

    @DeleteMapping(path = "/{ratingId}")
    public ResponseEntity<?> deleteRating(Principal principal, @PathVariable(value = "ratingId") Long id) {
        Rating rating = this.ratingService.findById(id);
        Optional.ofNullable(rating).orElseThrow(() -> new EntityNotFoundException("Không tìm thấy đánh giá"));

        User user = this.userService.findByUsername(principal.getName());
        if (!Objects.equals(user.getId(), rating.getUser().getId())) {
            throw new AccessDeniedException("Bạn không có quyền xóa đánh giá này");
        }

        this.ratingService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(@NotNull HttpServletRequest req, EntityNotFoundException e) {
        LoggerFactory.getLogger(req.getRequestURI()).error(e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of(new MessageResponse(e.getMessage())));
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<?> handleIllegalArgumentException(@NotNull HttpServletRequest req, IllegalArgumentException e) {
        LoggerFactory.getLogger(req.getRequestURI()).error(e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(List.of(new MessageResponse(e.getMessage())));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDenied(@NotNull HttpServletRequest req, AccessDeniedException e) {
        LoggerFactory.getLogger(req.getRequestURI()).error(e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(List.of(new MessageResponse(e.getMessage())));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(@NotNull HttpServletRequest req, Exception e) {
        LoggerFactory.getLogger(req.getRequestURI()).error(e.getMessage(), e);

        return ResponseEntity.badRequest().body(List.of(new MessageResponse(e.getMessage())));
    }
}
