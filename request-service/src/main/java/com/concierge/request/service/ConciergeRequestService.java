package com.concierge.request.service;

import com.concierge.request.dto.*;
import com.concierge.request.model.*;
import com.concierge.request.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ConciergeRequestService {

    private final ConciergeRequestRepository requestRepository;
    private final ServiceCategoryRepository categoryRepository;
    private final RequestCommentRepository commentRepository;
    private final RequestReviewRepository reviewRepository;

    public ConciergeRequestService(ConciergeRequestRepository requestRepository,
                                   ServiceCategoryRepository categoryRepository,
                                   RequestCommentRepository commentRepository,
                                   RequestReviewRepository reviewRepository) {
        this.requestRepository = requestRepository;
        this.categoryRepository = categoryRepository;
        this.commentRepository = commentRepository;
        this.reviewRepository = reviewRepository;
    }

    public ConciergeRequestDTO createRequest(Long userId, CreateRequestDTO dto) {
        ServiceCategory category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        ConciergeRequest request = new ConciergeRequest();
        request.setUserId(userId);
        request.setCategory(category);
        request.setTitle(dto.getTitle());
        request.setDescription(dto.getDescription());
        request.setPriority(dto.getPriority());
        request.setStatus(RequestStatus.PENDING);

        ConciergeRequest savedRequest = requestRepository.save(request);
        return mapToDTO(savedRequest);
    }

    public List<ConciergeRequestDTO> getUserRequests(Long userId) {
        return requestRepository.findByUserId(userId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public ConciergeRequestDTO getRequestById(Long requestId) {
        ConciergeRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        return mapToDTO(request);
    }

    public ConciergeRequestDTO updateRequest(Long requestId, CreateRequestDTO dto) {
        ConciergeRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (dto.getCategoryId() != null) {
            ServiceCategory category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            request.setCategory(category);
        }

        if (dto.getTitle() != null) {
            request.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null) {
            request.setDescription(dto.getDescription());
        }
        if (dto.getPriority() != null) {
            request.setPriority(dto.getPriority());
        }

        ConciergeRequest updatedRequest = requestRepository.save(request);
        return mapToDTO(updatedRequest);
    }

    public ConciergeRequestDTO updateRequestStatus(Long requestId, RequestStatus status) {
        ConciergeRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        request.setStatus(status);
        if (status == RequestStatus.COMPLETED) {
            request.setCompletedAt(LocalDateTime.now());
        }

        ConciergeRequest updatedRequest = requestRepository.save(request);
        return mapToDTO(updatedRequest);
    }

    public void deleteRequest(Long requestId) {
        if (!requestRepository.existsById(requestId)) {
            throw new RuntimeException("Request not found");
        }
        requestRepository.deleteById(requestId);
    }

    public RequestCommentDTO addComment(Long requestId, Long userId, String message) {
        ConciergeRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        RequestComment comment = new RequestComment();
        comment.setRequest(request);
        comment.setUserId(userId);
        comment.setMessage(message);

        RequestComment savedComment = commentRepository.save(comment);
        return mapCommentToDTO(savedComment);
    }

    public List<RequestCommentDTO> getRequestComments(Long requestId) {
        return commentRepository.findByRequestId(requestId)
                .stream()
                .map(this::mapCommentToDTO)
                .collect(Collectors.toList());
    }

    private ConciergeRequestDTO mapToDTO(ConciergeRequest request) {
        ConciergeRequestDTO dto = new ConciergeRequestDTO();
        dto.setId(request.getId());
        dto.setUserId(request.getUserId());
        dto.setCategoryId(request.getCategory().getId());
        dto.setCategory(mapCategoryToDTO(request.getCategory()));
        dto.setTitle(request.getTitle());
        dto.setDescription(request.getDescription());
        dto.setStatus(request.getStatus());
        dto.setPriority(request.getPriority());
        dto.setCreatedAt(request.getCreatedAt());
        dto.setUpdatedAt(request.getUpdatedAt());
        dto.setCompletedAt(request.getCompletedAt());
        return dto;
    }

    private ServiceCategoryDTO mapCategoryToDTO(ServiceCategory category) {
        return new ServiceCategoryDTO(category.getId(), category.getName(),
                category.getDescription(), category.getIcon());
    }

    private RequestCommentDTO mapCommentToDTO(RequestComment comment) {
        return new RequestCommentDTO(comment.getId(), comment.getRequest().getId(),
                comment.getUserId(), comment.getMessage(), comment.getCreatedAt());
    }
}
