package com.georeport.repository;

import com.georeport.entity.PublicComplaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PublicComplaintRepository extends JpaRepository<PublicComplaint, Long> {
    List<PublicComplaint> findByStatus(String status);
    List<PublicComplaint> findBySubmittedById(Long userId);
    
    @Query("SELECT COUNT(pc) FROM PublicComplaint pc WHERE pc.status = ?1")
    Long countByStatus(String status);
}
