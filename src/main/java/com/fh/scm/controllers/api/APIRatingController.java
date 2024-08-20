package com.fh.scm.controllers.api;

import com.fh.scm.dto.ResponseMessage;
import com.fh.scm.dto.api.rating.RatingRequestUpdate;
import com.fh.scm.pojo.Rating;
import com.fh.scm.pojo.User;
import com.fh.scm.services.RatingService;
import com.fh.scm.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/rating", produces = "application/json; charset=UTF-8")
public class APIRatingController {

    private final RatingService ratingService;
    private final UserService userService;

    @GetMapping(path = "/list")
    public ResponseEntity<?> list(@RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        List<Rating> ratingList = this.ratingService.getAll(params);

        return ResponseEntity.ok(ratingList);
    }

    @GetMapping(path = "/{ratingId}/retrieve")
    public ResponseEntity<?> retrieve(@PathVariable(value = "ratingId") Long id) {
        Rating rating = this.ratingService.get(id);

        if (rating == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(rating);
    }

    @PostMapping(path = "/{ratingId}/edit")
    public ResponseEntity<?> details(Principal principal, @PathVariable(value = "ratingId") Long id,
                                     @ModelAttribute @Valid RatingRequestUpdate ratingRequestUpdate, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ResponseMessage> responseMessages = ResponseMessage.fromBindingResult(bindingResult);

            return ResponseEntity.badRequest().body(responseMessages);
        }

        Rating rating = this.ratingService.get(id);

        if (rating == null) {
            return ResponseEntity.notFound().build();
        }

        User user = this.userService.getByUsername(principal.getName());
        if (Objects.equals(user.getId(), rating.getUser().getId())) {
            rating = this.ratingService.update(rating, ratingRequestUpdate);

            return ResponseEntity.ok(rating);
        }

        return ResponseEntity.badRequest().body(new ResponseMessage("Bạn không có quyền sửa đánh giá này"));
    }

    @DeleteMapping(path = "/{ratingId}/delete")
    public ResponseEntity<?> delete(Principal principal, @PathVariable(value = "ratingId") Long id) {
        Rating rating = this.ratingService.get(id);

        if (rating == null) {
            return ResponseEntity.notFound().build();
        }

        User user = this.userService.getByUsername(principal.getName());
        if (Objects.equals(user.getId(), rating.getUser().getId())) {
            this.ratingService.delete(id);

            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.badRequest().body(new ResponseMessage("Bạn không có quyền xóa đánh giá này"));
    }
}
