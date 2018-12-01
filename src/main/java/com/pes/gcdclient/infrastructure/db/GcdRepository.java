package com.pes.gcdclient.infrastructure.db;

import com.pes.gcdclient.infrastructure.db.entity.GcdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GcdRepository extends JpaRepository<GcdEntity, Long> {
    Optional<GcdEntity> findByFirstAndSecond(Long first, Long second);
}
