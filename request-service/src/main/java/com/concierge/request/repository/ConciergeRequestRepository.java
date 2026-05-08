package com.concierge.request.repository;

import com.concierge.request.model.ConciergeRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ConciergeRequestRepository extends JpaRepository<ConciergeRequest, Long> {
    List<ConciergeRequest> findByUserId(Long userId);
}
