package com.github.lxgang.jpa.persistence.mysql.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.github.lxgang.jpa.beans.User;

public interface UserRepository extends CrudRepository<User, Integer>{
	public List<User> findByName(String name);
	
	@Query("from user u where u.name = :name AND u.age > 18")
	public List<User> findByQuery(@Param("name")String name);
	
	public Page<User> findByName(String name, Pageable pageable);
}
