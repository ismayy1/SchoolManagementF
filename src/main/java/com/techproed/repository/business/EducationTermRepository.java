package com.techproed.repository.business;

import com.techproed.entity.concretes.business.EducationTerm;
import com.techproed.entity.enums.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationTermRepository extends JpaRepository<EducationTerm, Long> {

    @Query("SELECT (COUNT (e) > 0) FROM EducationTerm e WHERE e.term=?1 AND EXTRACT(year FROM e.startDate) =?2")
    boolean existsByTermAndYear(Term term, int year);

//    year from input exists
    @Query("SELECT e FROM EducationTerm e WHERE EXTRACT(YEAR FROM e.startDate) =?1")
    List<EducationTerm> findByYear(int year);
}
