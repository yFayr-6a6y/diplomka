package com.example.diplomka.service;

import com.example.diplomka.dto.CreateOrUpdateComment;
import com.example.diplomka.entity.AdEntity;
import com.example.diplomka.entity.CommentEntity;
import com.example.diplomka.entity.UserEntity;
import com.example.diplomka.repository.AdRepository;
import com.example.diplomka.repository.CommentRepository;
import com.example.diplomka.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final AdRepository adRepository;
    private final UserRepository userRepository;

    public List<CommentEntity> getComments(Integer adId) {
        return commentRepository.findAllByAd_Pk(adId);
    }

    public CommentEntity addComment(Integer adId, CreateOrUpdateComment dto, Authentication auth) {
        AdEntity ad = adRepository.findById(adId).orElseThrow();
        UserEntity author = userRepository.findByEmail(auth.getName()).orElseThrow();

        CommentEntity comment = new CommentEntity();
        comment.setText(dto.getText());
        comment.setCreatedAt(System.currentTimeMillis());
        comment.setAd(ad);
        comment.setAuthor(author);
        return commentRepository.save(comment);
    }

    public CommentEntity updateComment(Integer adId, Integer commentId, CreateOrUpdateComment dto, Authentication auth) {
        CommentEntity comment = commentRepository.findById(commentId).orElseThrow();
        checkPermissions(comment.getAuthor().getEmail(), auth);
        comment.setText(dto.getText());
        return commentRepository.save(comment);
    }

    public void deleteComment(Integer adId, Integer commentId, Authentication auth) {
        CommentEntity comment = commentRepository.findById(commentId).orElseThrow();
        checkPermissions(comment.getAuthor().getEmail(), auth);
        commentRepository.delete(comment);
    }

    private void checkPermissions(String authorEmail, Authentication auth) {
        boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().contains("ROLE_ADMIN"));
        if (!authorEmail.equals(auth.getName()) && !isAdmin) {
            throw new AccessDeniedException("Нет прав на этот комментарий");
        }
    }
}