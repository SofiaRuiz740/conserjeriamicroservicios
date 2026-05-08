package com.concierge.request.repository;

import com.concierge.request.model.RequestComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RequestCommentRepository extends JpaRepository<RequestComment, Long> {
    List<RequestComment> findByRequestId(Long requestId);
}
