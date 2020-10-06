package com.qa.Api.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.qa.Api.persistence.domain.Task;
import com.qa.Api.persistence.domain.TaskList;

@Repository
public interface TaskListRepository extends JpaRepository<TaskList, Long>{

}
