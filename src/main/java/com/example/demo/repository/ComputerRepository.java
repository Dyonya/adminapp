package com.example.demo.repository;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "computer", path = "computer")
public interface ComputerRepository extends PagingAndSortingRepository<com.example.demo.repository.Computer, Long>, CrudRepository<com.example.demo.repository.Computer,Long> {

    com.example.demo.repository.Computer findByComputerName(@Param("computerName") String computerName);
    List<com.example.demo.repository.Computer> findByEmployeeAbbreviation(@Param("employeeAbbreviation") String employeeAbbreviation);
}

