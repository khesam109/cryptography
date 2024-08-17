package com.khesam.papyrus.core.repository;

import com.khesam.papyrus.core.repository.entity.SignerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignerRepository extends JpaRepository<SignerEntity, Integer> {
}
