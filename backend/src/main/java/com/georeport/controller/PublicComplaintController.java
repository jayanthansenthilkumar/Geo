package com.georeport.controller;

import com.georeport.dto.ApiResponse;
import com.georeport.entity.Issue;
import com.georeport.entity.PublicComplaint;
import com.georeport.service.PublicComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin(origins = "*")
public class PublicComplaintController {

    @Autowired
    private PublicComplaintService complaintService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GOVERNMENT')")
    public ResponseEntity<List<PublicComplaint>> getAllComplaints() {
        return ResponseEntity.ok(complaintService.getAllComplaints());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getComplaintById(@PathVariable Long id) {
        try {
            PublicComplaint complaint = complaintService.getComplaintById(id);
            return ResponseEntity.ok(complaint);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GOVERNMENT')")
    public ResponseEntity<List<PublicComplaint>> getComplaintsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(complaintService.getComplaintsByStatus(status));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PublicComplaint>> getComplaintsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(complaintService.getComplaintsByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<?> createComplaint(@RequestBody PublicComplaint complaint,
                                            @RequestParam Long userId) {
        try {
            PublicComplaint createdComplaint = complaintService.createComplaint(complaint, userId);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Complaint submitted successfully", createdComplaint));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasAnyRole('ADMIN', 'GOVERNMENT')")
    public ResponseEntity<?> approveComplaint(@PathVariable Long id, @RequestParam Long reviewerId) {
        try {
            PublicComplaint complaint = complaintService.approveComplaint(id, reviewerId);
            return ResponseEntity.ok(new ApiResponse(true, "Complaint approved", complaint));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    @PutMapping("/{id}/reject")
    @PreAuthorize("hasAnyRole('ADMIN', 'GOVERNMENT')")
    public ResponseEntity<?> rejectComplaint(@PathVariable Long id, 
                                             @RequestParam Long reviewerId,
                                             @RequestBody Map<String, String> body) {
        try {
            String reason = body.get("reason");
            PublicComplaint complaint = complaintService.rejectComplaint(id, reviewerId, reason);
            return ResponseEntity.ok(new ApiResponse(true, "Complaint rejected", complaint));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    @PostMapping("/{id}/convert")
    @PreAuthorize("hasAnyRole('ADMIN', 'GOVERNMENT')")
    public ResponseEntity<?> convertToIssue(@PathVariable Long id) {
        try {
            Issue issue = complaintService.convertComplaintToIssue(id);
            return ResponseEntity.ok(new ApiResponse(true, "Complaint converted to issue", issue));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteComplaint(@PathVariable Long id) {
        try {
            complaintService.deleteComplaint(id);
            return ResponseEntity.ok(new ApiResponse(true, "Complaint deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
}
