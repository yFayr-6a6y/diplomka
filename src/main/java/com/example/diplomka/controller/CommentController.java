package com.example.diplomka.controller;

import com.example.diplomka.dto.Comment;
import com.example.diplomka.dto.Comments;
import com.example.diplomka.dto.CreateOrUpdateComment;
import com.example.diplomka.mapper.CommentMapper;
import com.example.diplomka.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads/{adId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @GetMapping
    public ResponseEntity<Comments> getComments(@PathVariable Integer adId) {
        var list = commentService.getComments(adId).stream().map(commentMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(new Comments(list.size(), list));
    }

    @PostMapping
    public ResponseEntity<Comment> addComment(@PathVariable Integer adId, @RequestBody CreateOrUpdateComment text, Authentication auth) {
        return ResponseEntity.ok(commentMapper.toDTO(commentService.addComment(adId, text, auth)));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer adId, @PathVariable Integer commentId, Authentication auth) {
        commentService.deleteComment(adId, commentId, auth);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Integer adId, @PathVariable Integer commentId, @RequestBody CreateOrUpdateComment text, Authentication auth) {
        return ResponseEntity.ok(commentMapper.toDTO(commentService.updateComment(adId, commentId, text, auth)));
    }
}