package com.georeport.service;

import com.georeport.entity.Issue;
import com.georeport.entity.PublicComplaint;
import com.georeport.entity.PublicUser;
import com.georeport.entity.User;
import com.georeport.repository.IssueRepository;
import com.georeport.repository.PublicComplaintRepository;
import com.georeport.repository.PublicUserRepository;
import com.georeport.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PublicComplaintService {

    @Autowired
    private PublicComplaintRepository complaintRepository;

    @Autowired
    private PublicUserRepository publicUserRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IssueRepository issueRepository;

    public List<PublicComplaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    public PublicComplaint getComplaintById(Long id) {
        return complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found with id: " + id));
    }

    public List<PublicComplaint> getComplaintsByStatus(String status) {
        return complaintRepository.findByStatus(status);
    }

    public List<PublicComplaint> getComplaintsByUserId(Long userId) {
        return complaintRepository.findBySubmittedById(userId);
    }

    @Transactional
    public PublicComplaint createComplaint(PublicComplaint complaint, Long userId) {
        PublicUser user = publicUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        
        complaint.setSubmittedBy(user);
        complaint.setStatus("pending");
        complaint.setCreatedAt(LocalDateTime.now());
        complaint.setUpdatedAt(LocalDateTime.now());
        
        return complaintRepository.save(complaint);
    }

    @Transactional
    public PublicComplaint approveComplaint(Long complaintId, Long reviewerUserId) {
        PublicComplaint complaint = getComplaintById(complaintId);
        User reviewer = userRepository.findById(reviewerUserId)
                .orElseThrow(() -> new RuntimeException("Reviewer not found"));

        complaint.setStatus("approved");
        complaint.setReviewedBy(reviewer);
        complaint.setReviewedAt(LocalDateTime.now());
        complaint.setUpdatedAt(LocalDateTime.now());

        return complaintRepository.save(complaint);
    }

    @Transactional
    public PublicComplaint rejectComplaint(Long complaintId, Long reviewerUserId, String reason) {
        PublicComplaint complaint = getComplaintById(complaintId);
        User reviewer = userRepository.findById(reviewerUserId)
                .orElseThrow(() -> new RuntimeException("Reviewer not found"));

        complaint.setStatus("rejected");
        complaint.setReviewedBy(reviewer);
        complaint.setReviewedAt(LocalDateTime.now());
        complaint.setRejectionReason(reason);
        complaint.setUpdatedAt(LocalDateTime.now());

        return complaintRepository.save(complaint);
    }

    @Transactional
    public Issue convertComplaintToIssue(Long complaintId) {
        PublicComplaint complaint = getComplaintById(complaintId);

        // Create new issue from complaint
        Issue issue = new Issue();
        issue.setTitle(complaint.getTitle());
        issue.setDescription(complaint.getDescription());
        issue.setCategory(complaint.getCategory());
        issue.setPriority("medium"); // Default priority
        issue.setStatus("pending");
        issue.setLatitude(complaint.getLatitude());
        issue.setLongitude(complaint.getLongitude());
        issue.setLocation(complaint.getLocation());
        issue.setReporterName(complaint.getSubmittedBy().getFullName());
        issue.setReporterContact(complaint.getSubmittedBy().getPhoneNumber());
        issue.setImageUrl(complaint.getImageUrl());

        Issue savedIssue = issueRepository.save(issue);

        // Update complaint status
        complaint.setStatus("converted");
        complaint.setConvertedIssueId(savedIssue.getId());
        complaint.setUpdatedAt(LocalDateTime.now());
        complaintRepository.save(complaint);

        return savedIssue;
    }

    @Transactional
    public void deleteComplaint(Long id) {
        PublicComplaint complaint = getComplaintById(id);
        complaintRepository.delete(complaint);
    }
}
