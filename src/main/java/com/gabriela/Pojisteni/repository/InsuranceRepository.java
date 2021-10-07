package com.gabriela.Pojisteni.repository;

import com.gabriela.Pojisteni.model.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Integer> {
    @Query("SELECT COUNT(*) FROM Insurance i WHERE i.insurance_id = :insurance_id")
    Long countByInsurance_id(@Param("insurance_id") Integer insurance_id);
    @Query("SELECT i FROM Insurance i WHERE i.insurance_id = :insurance_id")
    Optional<Insurance> findByInsurance_id(@Param("insurance_id") Integer insurance_id);
    @Transactional
    @Modifying
    @Query("DELETE FROM Insurance i WHERE i.insurance_id = :insurance_id")
    void deleteByInsurance_id(@Param("insurance_id") Integer insurance_id);
}
