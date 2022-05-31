package com.ioco.bots.tebello.robot_apocalypse.repository;

import com.ioco.bots.tebello.robot_apocalypse.entity.Survivor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurvivorRepository extends JpaRepository<Survivor, Long> {}
