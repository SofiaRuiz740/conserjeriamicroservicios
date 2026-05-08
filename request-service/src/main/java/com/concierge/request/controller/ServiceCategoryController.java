package com.concierge.request.controller;

import com.concierge.request.dto.ServiceCategoryDTO;
import com.concierge.request.service.ServiceCategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/request-service/categories")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ServiceCategoryController {

    private final ServiceCategoryService categoryService;

    public ServiceCategoryController(ServiceCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<ServiceCategoryDTO>> getAllCategories() {
        List<ServiceCategoryDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceCategoryDTO> getCategoryById(@PathVariable Long id) {
        ServiceCategoryDTO category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity<ServiceCategoryDTO> createCategory(@Valid @RequestBody ServiceCategoryDTO dto) {
        ServiceCategoryDTO response = categoryService.createCategory(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceCategoryDTO> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody ServiceCategoryDTO dto) {
        ServiceCategoryDTO response = categoryService.updateCategory(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
