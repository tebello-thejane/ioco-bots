package com.ioco.bots.tebello.robot_apocalypse.repository;

import com.ioco.bots.tebello.robot_apocalypse.model.Survivor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurvivorRepository extends JpaRepository<Survivor, Long> {
    List<Survivor> findByInfected(boolean infected);
}
