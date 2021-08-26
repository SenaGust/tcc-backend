package com.tcc.backend.resources;
import com.tcc.backend.entity.User;

import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Integer> {

}