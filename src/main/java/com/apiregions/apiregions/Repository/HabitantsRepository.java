package com.apiregions.apiregions.Repository;

import com.apiregions.apiregions.Models.Habitants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitantsRepository extends JpaRepository<Habitants, Long> {
}
