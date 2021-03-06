/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labguis.gfour.repository;

import com.labguis.gfour.modelo.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Makot
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByName(String nombre);
    User findByEmail(String email);
}
