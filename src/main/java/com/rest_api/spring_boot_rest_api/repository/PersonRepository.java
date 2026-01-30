package com.rest_api.spring_boot_rest_api.repository;

import com.rest_api.spring_boot_rest_api.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Modifying(clearAutomatically = true) // transaction control -> ACID clear automatically hibernate cache.
    @Query("UPDATE Person p set p.enabled = false WHERE p.id =:id") // query for database.
    void disablePerson(@Param("id") Long id);
}
