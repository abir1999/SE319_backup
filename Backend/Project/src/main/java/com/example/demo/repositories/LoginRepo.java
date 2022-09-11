package com.example.demo.repositories;
import com.example.demo.models.Login_page;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepo extends JpaRepository<Login_page,Integer> {

	List<Login_page> findAll();
	
	@SuppressWarnings("unchecked")
	Login_page save(Login_page user);
	
	
	//List<Login> findByUsernameAndPass(String username, String password);

	
	@Query(value = "SELECT * FROM loginpage WHERE username = ?1", nativeQuery = true)
	Login_page findByUsername(String username);
	
	@Query(value = "SELECT * FROM loginpage WHERE password = ?1", nativeQuery = true)
	Login_page findByPassword(String password);
}
