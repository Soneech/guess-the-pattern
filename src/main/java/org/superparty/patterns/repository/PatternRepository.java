package org.superparty.patterns.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.superparty.patterns.model.Pattern;

@Repository
public interface PatternRepository extends JpaRepository<Pattern, Long> {
}
