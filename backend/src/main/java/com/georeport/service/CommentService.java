package com.georeport.service;

import com.georeport.entity.Comment;
import com.georeport.entity.Issue;
import com.georeport.repository.CommentRepository;
import com.georeport.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private IssueRepository issueRepository;

    public List<Comment> getCommentsByIssueId(Long issueId) {
        return commentRepository.findByIssueIdOrderByCreatedAtDesc(issueId);
    }

    @Transactional
    public Comment createComment(Comment comment, Long issueId) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found with id: " + issueId));
        
        comment.setIssue(issue);
        comment.setCreatedAt(LocalDateTime.now());
        
        return commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + id));
        commentRepository.delete(comment);
    }
}
