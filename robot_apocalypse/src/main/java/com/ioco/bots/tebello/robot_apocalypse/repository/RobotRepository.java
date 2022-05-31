package com.ioco.bots.tebello.robot_apocalypse.repository;

import com.ioco.bots.tebello.robot_apocalypse.model.Robot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RobotRepository extends JpaRepository<Robot, Long> {}
