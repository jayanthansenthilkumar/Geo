package com.georeport.controller;

import com.georeport.dto.ApiResponse;
import com.georeport.entity.Issue;
import com.georeport.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/issues")
@CrossOrigin(origins = "*")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @GetMapping
    public ResponseEntity<List<Issue>> getAllIssues() {
        return ResponseEntity.ok(issueService.getAllIssues());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getIssueById(@PathVariable Long id) {
        try {
            Issue issue = issueService.getIssueById(id);
            return ResponseEntity.ok(issue);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Issue>> getIssuesByStatus(@PathVariable String status) {
        return ResponseEntity.ok(issueService.getIssuesByStatus(status));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Issue>> getIssuesByCategory(@PathVariable String category) {
        return ResponseEntity.ok(issueService.getIssuesByCategory(category));
    }

    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        return ResponseEntity.ok(issueService.getStatistics());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GOVERNMENT')")
    public ResponseEntity<?> createIssue(@RequestBody Issue issue) {
        try {
            Issue createdIssue = issueService.createIssue(issue);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Issue created successfully", createdIssue));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GOVERNMENT')")
    public ResponseEntity<?> updateIssue(@PathVariable Long id, @RequestBody Issue issue) {
        try {
            Issue updatedIssue = issueService.updateIssue(id, issue);
            return ResponseEntity.ok(new ApiResponse(true, "Issue updated successfully", updatedIssue));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    @PutMapping("/{issueId}/assign/{workerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> assignWorker(@PathVariable Long issueId, @PathVariable Long workerId) {
        try {
            Issue updatedIssue = issueService.assignWorker(issueId, workerId);
            return ResponseEntity.ok(new ApiResponse(true, "Worker assigned successfully", updatedIssue));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteIssue(@PathVariable Long id) {
        try {
            issueService.deleteIssue(id);
            return ResponseEntity.ok(new ApiResponse(true, "Issue deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
}
