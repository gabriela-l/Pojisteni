package com.gabriela.Pojisteni.service;

import com.gabriela.Pojisteni.model.Insurance;
import com.gabriela.Pojisteni.controller.UserNotFoundException;
import com.gabriela.Pojisteni.repository.InsuranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InsuranceService {
    // reference na repo (extends JpaRepository)
    @Autowired
    private InsuranceRepository repo;

    public List<Insurance> listAll() {
        return (List<Insurance>) repo.findAll(); }

    public void save(Insurance insurance) {
        repo.save(insurance);
    }

    public Insurance get(Integer insurance_id) throws UserNotFoundException {
        Optional<Insurance> result = repo.findByInsurance_id(insurance_id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new UserNotFoundException("Nebylo nalezeno pojištění s ID " + insurance_id);
    }

    public void delete(Integer insurance_id) throws UserNotFoundException {
        Long count = repo.countByInsurance_id(insurance_id);
        if (count == null || count == 0) {
            throw new UserNotFoundException("Nebylo nalezeno pojištění s ID " + insurance_id);
        }
        repo.deleteByInsurance_id(insurance_id);}

}

