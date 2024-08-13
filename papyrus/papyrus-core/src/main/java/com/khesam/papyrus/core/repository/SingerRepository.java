package com.khesam.papyrus.core.repository;

import com.khesam.papyrus.core.repository.entity.SignerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SingerRepository extends JpaRepository<SignerEntity, Integer> {
}
