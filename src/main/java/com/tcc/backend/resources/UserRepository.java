package com.tcc.backend.resources;
import com.tcc.backend.models.User;

import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Integer> {

}