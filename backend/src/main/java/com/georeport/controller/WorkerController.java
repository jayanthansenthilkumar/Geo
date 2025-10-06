package com.georeport.controller;

import com.georeport.dto.ApiResponse;
import com.georeport.entity.Worker;
import com.georeport.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workers")
@CrossOrigin(origins = "*")
@PreAuthorize("hasAnyRole('ADMIN', 'GOVERNMENT')")
public class WorkerController {

    @Autowired
    private WorkerService workerService;

    @GetMapping
    public ResponseEntity<List<Worker>> getAllWorkers() {
        return ResponseEntity.ok(workerService.getAllWorkers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getWorkerById(@PathVariable Long id) {
        try {
            Worker worker = workerService.getWorkerById(id);
            return ResponseEntity.ok(worker);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<Worker>> getWorkersByDepartment(@PathVariable String department) {
        return ResponseEntity.ok(workerService.getWorkersByDepartment(department));
    }

    @GetMapping("/available")
    public ResponseEntity<List<Worker>> getAvailableWorkers() {
        return ResponseEntity.ok(workerService.getAvailableWorkers());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createWorker(@RequestBody Worker worker) {
        try {
            Worker createdWorker = workerService.createWorker(worker);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Worker created successfully", createdWorker));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateWorker(@PathVariable Long id, @RequestBody Worker worker) {
        try {
            Worker updatedWorker = workerService.updateWorker(id, worker);
            return ResponseEntity.ok(new ApiResponse(true, "Worker updated successfully", updatedWorker));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteWorker(@PathVariable Long id) {
        try {
            workerService.deleteWorker(id);
            return ResponseEntity.ok(new ApiResponse(true, "Worker deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
}
