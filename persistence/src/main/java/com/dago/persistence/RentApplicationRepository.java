package com.dago.persistence;

import com.dago.domain.RentApplication;
import com.dago.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RentApplicationRepository extends JpaRepository<RentApplication, Integer> {
    Page<RentApplication> findAllByApplicant(User user, Pageable pageable);
}
