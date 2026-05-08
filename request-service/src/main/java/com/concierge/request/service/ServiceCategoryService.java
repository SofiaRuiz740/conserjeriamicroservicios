package com.concierge.request.service;

import com.concierge.request.dto.ServiceCategoryDTO;
import com.concierge.request.model.ServiceCategory;
import com.concierge.request.repository.ServiceCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServiceCategoryService {

    private final ServiceCategoryRepository categoryRepository;

    public ServiceCategoryService(ServiceCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<ServiceCategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public ServiceCategoryDTO getCategoryById(Long id) {
        ServiceCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return mapToDTO(category);
    }

    public ServiceCategoryDTO createCategory(ServiceCategoryDTO dto) {
        ServiceCategory category = new ServiceCategory();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setIcon(dto.getIcon());

        ServiceCategory savedCategory = categoryRepository.save(category);
        return mapToDTO(savedCategory);
    }

    public ServiceCategoryDTO updateCategory(Long id, ServiceCategoryDTO dto) {
        ServiceCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (dto.getName() != null) {
            category.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            category.setDescription(dto.getDescription());
        }
        if (dto.getIcon() != null) {
            category.setIcon(dto.getIcon());
        }

        ServiceCategory updatedCategory = categoryRepository.save(category);
        return mapToDTO(updatedCategory);
    }

    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found");
        }
        categoryRepository.deleteById(id);
    }

    private ServiceCategoryDTO mapToDTO(ServiceCategory category) {
        return new ServiceCategoryDTO(category.getId(), category.getName(),
                category.getDescription(), category.getIcon());
    }
}
