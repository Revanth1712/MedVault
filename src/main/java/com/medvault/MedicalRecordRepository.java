
package com.medvault;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicalRecordRepository
        extends JpaRepository<MedicalRecord, Long> {

    // Search records belonging to a specific user
    List<MedicalRecord> findByUserIdAndPatientNameContainingIgnoreCase(
            Long userId, String patientName);

    // Filter by category for a specific user
    List<MedicalRecord> findByUserIdAndRecordTypeIgnoreCase(
            Long userId, String recordType);

    // Sort a user's records by date, newest first
    List<MedicalRecord> findByUserIdOrderByRecordDateDesc(
            Long userId);

    // Paginated category filtering for a specific user
    Page<MedicalRecord> findByUserIdAndRecordTypeIgnoreCase(
            Long userId, String recordType, Pageable pageable);

    // Count a user's records by category
    long countByUserIdAndRecordTypeIgnoreCase(
            Long userId, String recordType);

    // Find one record only if it belongs to the specified user
    Optional<MedicalRecord> findByIdAndUserId(
            Long id, Long userId);

    // Get all records belonging to a specific user
    Page<MedicalRecord> findByUserId(
            Long userId, Pageable pageable);

    List<MedicalRecord> findByUserId(Long userId);

    long countByUserId(Long userId);
}
