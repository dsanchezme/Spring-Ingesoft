package com.labguis.gfour.repository;

import com.labguis.gfour.modelo.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nicolas Castillo
 */
@Repository
public interface ILocation extends CrudRepository<Location, Integer>{
    Location findByName(String name);
    Location findByNumberId(String value);
}
