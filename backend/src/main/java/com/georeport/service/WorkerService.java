package com.georeport.service;

import com.georeport.entity.Worker;
import com.georeport.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WorkerService {

    @Autowired
    private WorkerRepository workerRepository;

    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }

    public Worker getWorkerById(Long id) {
        return workerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Worker not found with id: " + id));
    }

    public List<Worker> getWorkersByDepartment(String department) {
        return workerRepository.findByDepartment(department);
    }

    public List<Worker> getAvailableWorkers() {
        return workerRepository.findByIsAvailable(true);
    }

    @Transactional
    public Worker createWorker(Worker worker) {
        if (workerRepository.existsByEmail(worker.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        worker.setIsAvailable(true);
        return workerRepository.save(worker);
    }

    @Transactional
    public Worker updateWorker(Long id, Worker workerDetails) {
        Worker worker = getWorkerById(id);
        
        if (workerDetails.getName() != null) {
            worker.setName(workerDetails.getName());
        }
        if (workerDetails.getDepartment() != null) {
            worker.setDepartment(workerDetails.getDepartment());
        }
        if (workerDetails.getPhoneNumber() != null) {
            worker.setPhoneNumber(workerDetails.getPhoneNumber());
        }
        if (workerDetails.getEmail() != null) {
            worker.setEmail(workerDetails.getEmail());
        }
        if (workerDetails.getSpecialization() != null) {
            worker.setSpecialization(workerDetails.getSpecialization());
        }
        if (workerDetails.getIsAvailable() != null) {
            worker.setIsAvailable(workerDetails.getIsAvailable());
        }
        
        return workerRepository.save(worker);
    }

    @Transactional
    public void deleteWorker(Long id) {
        Worker worker = getWorkerById(id);
        workerRepository.delete(worker);
    }
}
