
package com.medvault;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository repository;

    public MedicalRecordService(MedicalRecordRepository repository) {
        this.repository = repository;
    }

    public MedicalRecord saveRecord(MedicalRecord record) {
        return repository.save(record);
    }

    public List<MedicalRecord> getAllRecords(Long userId) {
        return repository.findByUserId(userId);
    }

    public long getTotalRecordCount(Long userId) {
        return repository.countByUserId(userId);
    }

    public Page<MedicalRecord> getRecordsPaginated(
            Long userId, int page, int size) {

        return repository.findByUserId(
                userId, PageRequest.of(page, size));
    }

    public List<MedicalRecord> searchRecordsByPatientName(
            Long userId, String patientName) {

        return repository
                .findByUserIdAndPatientNameContainingIgnoreCase(
                        userId, patientName);
    }

    public List<MedicalRecord> getRecordsByCategory(
            Long userId, String recordType) {

        return repository.findByUserIdAndRecordTypeIgnoreCase(
                userId, recordType);
    }

    public long getRecordCountByCategory(
            Long userId, String recordType) {

        return repository.countByUserIdAndRecordTypeIgnoreCase(
                userId, recordType);
    }

    public Page<MedicalRecord> getRecordsByCategoryPaginated(
            Long userId, String recordType, int page, int size) {

        return repository.findByUserIdAndRecordTypeIgnoreCase(
                userId, recordType, PageRequest.of(page, size));
    }

    public Optional<MedicalRecord> getRecordById(
            Long id, Long userId) {

        return repository.findByIdAndUserId(id, userId);
    }

    public List<MedicalRecord> getRecordsSortedByDate(Long userId) {
        return repository.findByUserIdOrderByRecordDateDesc(userId);
    }

    public Optional<MedicalRecord> updateRecord(
            Long id, Long userId, MedicalRecord updatedRecord) {

        return repository.findByIdAndUserId(id, userId)
                .map(existingRecord -> {
                    existingRecord.setPatientName(
                            updatedRecord.getPatientName());
                    existingRecord.setRecordType(
                            updatedRecord.getRecordType());
                    existingRecord.setTitle(
                            updatedRecord.getTitle());
                    existingRecord.setRecordDate(
                            updatedRecord.getRecordDate());
                    existingRecord.setDescription(
                            updatedRecord.getDescription());

                    return repository.save(existingRecord);
                });
    }

    public boolean deleteRecord(Long id, Long userId) {
        Optional<MedicalRecord> record =
                repository.findByIdAndUserId(id, userId);

        if (record.isEmpty()) {
            return false;
        }

        repository.delete(record.get());
        return true;
    }
}
