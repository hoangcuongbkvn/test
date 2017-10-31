package teamlab.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import teamlab.model.entity.Activity;

public interface ActivityDao extends JpaRepository<Activity, Integer> {

}