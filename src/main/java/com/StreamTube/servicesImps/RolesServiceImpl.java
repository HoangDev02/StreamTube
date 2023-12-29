package com.StreamTube.servicesImps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.StreamTube.models.UserRoles;
import com.StreamTube.services.UserRolesService;

@Service
public class RolesServiceImpl implements UserRolesService {
	@Autowired
	private com.StreamTube.repositories.UserRolesRepository UserRolesRepository;

	@Override
	public UserRoles createUserRoles(UserRoles userRoles) {
		return UserRolesRepository.save(userRoles);
	}

}
