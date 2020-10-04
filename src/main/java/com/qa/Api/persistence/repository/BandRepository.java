package com.qa.Api.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.Api.persistence.domain.Band;

@Repository
public interface BandRepository extends JpaRepository<Band, Long>{

}
