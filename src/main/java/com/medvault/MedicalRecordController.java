
package com.medvault;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/medical-records")
public class MedicalRecordController {

    private final MedicalRecordService service;
    private final UserRepository userRepository;

    public MedicalRecordController(
            MedicalRecordService service,
            UserRepository userRepository) {
        this.service = service;
        this.userRepository = userRepository;
    }

    
private Long getCurrentUserId(Authentication authentication) {
    if (authentication == null || !authentication.isAuthenticated()) {
        throw new org.springframework.security.authentication
                .AuthenticationCredentialsNotFoundException(
                "Authentication required");
    }

    return userRepository
            .findByEmailIgnoreCase(authentication.getName())
            .orElseThrow(() ->
                    new UsernameNotFoundException(
                            "Authenticated user not found"))
            .getId();
}


    
@PostMapping
public ResponseEntity<MedicalRecord> createRecord(
        @Valid @RequestBody MedicalRecord record,
        Authentication authentication) {

    Long userId = getCurrentUserId(authentication);

    User user = userRepository.findById(userId)
            .orElseThrow(() ->
                    new UsernameNotFoundException("User not found"));

    record.setUser(user);

    return ResponseEntity.status(201)
            .body(service.saveRecord(record));
}


    @GetMapping
    public List<MedicalRecord> getAllRecords(
            Authentication authentication) {
        return service.getAllRecords(getCurrentUserId(authentication));
    }

    @GetMapping("/count")
    public long getTotalRecordCount(Authentication authentication) {
        return service.getTotalRecordCount(
                getCurrentUserId(authentication));
    }

    @GetMapping("/search")
    public List<MedicalRecord> searchRecords(
            @RequestParam String patientName,
            Authentication authentication) {
        return service.searchRecordsByPatientName(
                getCurrentUserId(authentication), patientName);
    }

    @GetMapping("/category/{recordType}")
    public List<MedicalRecord> getRecordsByCategory(
            @PathVariable String recordType,
            Authentication authentication) {
        return service.getRecordsByCategory(
                getCurrentUserId(authentication), recordType);
    }

    @GetMapping("/category/{recordType}/paginated")
    public Page<MedicalRecord> getRecordsByCategoryPaginated(
            @PathVariable String recordType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Authentication authentication) {

        return service.getRecordsByCategoryPaginated(
                getCurrentUserId(authentication),
                recordType, page, size);
    }

    @GetMapping("/category/{recordType}/count")
    public long getRecordCountByCategory(
            @PathVariable String recordType,
            Authentication authentication) {

        return service.getRecordCountByCategory(
                getCurrentUserId(authentication), recordType);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecord> getRecordById(
            @PathVariable Long id,
            Authentication authentication) {

        return service.getRecordById(
                        id, getCurrentUserId(authentication))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/paginated")
    public Page<MedicalRecord> getRecordsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Authentication authentication) {

        return service.getRecordsPaginated(
                getCurrentUserId(authentication), page, size);
    }

    @GetMapping("/sorted-by-date")
    public List<MedicalRecord> getRecordsSortedByDate(
            Authentication authentication) {
        return service.getRecordsSortedByDate(
                getCurrentUserId(authentication));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalRecord> updateRecord(
            @PathVariable Long id,
            @Valid @RequestBody MedicalRecord record,
            Authentication authentication) {

        return service.updateRecord(
                        id, getCurrentUserId(authentication), record)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecord(
            @PathVariable Long id,
            Authentication authentication) {

        if (service.deleteRecord(
                id, getCurrentUserId(authentication))) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
