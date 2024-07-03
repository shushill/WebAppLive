package com.sushil.project.auth.application.controller;
import com.sushil.project.auth.application.serviceImpl.AuthCustomService;
import com.sushil.project.auth.domain.entity.Roles;
import com.sushil.project.auth.domain.entity.User;
import com.sushil.project.auth.domain.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AuthCustomServiceTest {
	private final UserRepo userRepo = Mockito.mock(UserRepo.class);
	private final AuthCustomService authCustomService = new AuthCustomService(userRepo);

	@Test
	public void testLoadUserByUsername_UserNotFound() {
		when(userRepo.findByUsername("testuser")).thenReturn(Optional.empty());
		assertThrows(UsernameNotFoundException.class, () -> authCustomService.loadUserByUsername("testuser"));
	}

	@Test
	public void testLoadUserByUsername_UserFound() {
		User user = new User();
		user.setUsername("testuser");
		user.setPassword("password");

		Set<Roles> roles = new HashSet<>();
		roles.add(new Roles(1L, "ROLE_USER"));
		user.setRoles(roles);

		// Mock behavior of userRepo.findByUsername()
		when(userRepo.findByUsername("testuser")).thenReturn(Optional.of(user));

		// Invoke method under test
		UserDetails userDetails = authCustomService.loadUserByUsername("testuser");

		// Assertions
		assertNotNull(userDetails);
		assertEquals("testuser", userDetails.getUsername());
		assertEquals("password", userDetails.getPassword());
		assertTrue(userDetails.getAuthorities().stream()
				.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER")));
	}
}
