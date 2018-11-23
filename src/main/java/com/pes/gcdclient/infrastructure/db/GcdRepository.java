package com.pes.gcdclient.infrastructure.db;

import com.pes.gcdclient.infrastructure.db.entity.GcdEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GcdRepository extends CrudRepository<GcdEntity, Long> {
}
