package com.efrei.asta.asta.repository;

import com.efrei.asta.asta.model.MaitreApprentissage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaitreApprentissageRepository extends JpaRepository<MaitreApprentissage, Integer> {
}

