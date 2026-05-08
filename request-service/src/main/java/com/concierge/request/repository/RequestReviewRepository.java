package com.concierge.request.repository;

import com.concierge.request.model.RequestReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestReviewRepository extends JpaRepository<RequestReview, Long> {
}
