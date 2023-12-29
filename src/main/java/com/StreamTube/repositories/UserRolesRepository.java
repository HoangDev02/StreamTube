package com.StreamTube.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.StreamTube.models.UserRoles;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRoles, Integer> {

}
