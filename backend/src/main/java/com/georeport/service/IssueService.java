package com.georeport.service;

import com.georeport.entity.Issue;
import com.georeport.entity.Worker;
import com.georeport.repository.IssueRepository;
import com.georeport.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private WorkerRepository workerRepository;

    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }

    public Issue getIssueById(Long id) {
        return issueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Issue not found with id: " + id));
    }

    public List<Issue> getIssuesByStatus(String status) {
        return issueRepository.findByStatus(status);
    }

    public List<Issue> getIssuesByCategory(String category) {
        return issueRepository.findByCategory(category);
    }

    @Transactional
    public Issue createIssue(Issue issue) {
        issue.setStatus("pending");
        issue.setCreatedAt(LocalDateTime.now());
        issue.setUpdatedAt(LocalDateTime.now());
        return issueRepository.save(issue);
    }

    @Transactional
    public Issue updateIssue(Long id, Issue issueDetails) {
        Issue issue = getIssueById(id);
        
        if (issueDetails.getTitle() != null) {
            issue.setTitle(issueDetails.getTitle());
        }
        if (issueDetails.getDescription() != null) {
            issue.setDescription(issueDetails.getDescription());
        }
        if (issueDetails.getCategory() != null) {
            issue.setCategory(issueDetails.getCategory());
        }
        if (issueDetails.getPriority() != null) {
            issue.setPriority(issueDetails.getPriority());
        }
        if (issueDetails.getStatus() != null) {
            issue.setStatus(issueDetails.getStatus());
            if ("resolved".equalsIgnoreCase(issueDetails.getStatus())) {
                issue.setResolvedAt(LocalDateTime.now());
            }
        }
        if (issueDetails.getLocation() != null) {
            issue.setLocation(issueDetails.getLocation());
        }
        
        issue.setUpdatedAt(LocalDateTime.now());
        return issueRepository.save(issue);
    }

    @Transactional
    public Issue assignWorker(Long issueId, Long workerId) {
        Issue issue = getIssueById(issueId);
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found with id: " + workerId));
        
        issue.setAssignedWorker(worker);
        issue.setStatus("in-progress");
        issue.setUpdatedAt(LocalDateTime.now());
        
        return issueRepository.save(issue);
    }

    @Transactional
    public void deleteIssue(Long id) {
        Issue issue = getIssueById(id);
        issueRepository.delete(issue);
    }

    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("total", issueRepository.count());
        stats.put("pending", issueRepository.countByStatus("pending"));
        stats.put("inProgress", issueRepository.countByStatus("in-progress"));
        stats.put("resolved", issueRepository.countByStatus("resolved"));
        stats.put("rejected", issueRepository.countByStatus("rejected"));
        
        List<Object[]> categoryStats = issueRepository.countByCategory();
        Map<String, Long> categoryMap = new HashMap<>();
        for (Object[] row : categoryStats) {
            categoryMap.put((String) row[0], (Long) row[1]);
        }
        stats.put("byCategory", categoryMap);
        
        return stats;
    }
}
