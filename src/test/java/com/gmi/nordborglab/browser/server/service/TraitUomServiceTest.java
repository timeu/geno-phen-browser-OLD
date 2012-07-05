package com.gmi.nordborglab.browser.server.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.gmi.nordborglab.browser.server.domain.acl.AppUser;
import com.gmi.nordborglab.browser.server.domain.acl.Authority;
import com.gmi.nordborglab.browser.server.domain.pages.TraitUomPage;
import com.gmi.nordborglab.browser.server.domain.phenotype.TraitUom;
import com.gmi.nordborglab.browser.server.repository.UserRepository;
import com.gmi.nordborglab.browser.server.testutils.BaseTest;
import com.gmi.nordborglab.browser.server.testutils.SecurityUtils;


public class TraitUomServiceTest extends BaseTest {
	
	@Resource
	private TraitUomService service;
	
	@Resource 
	private UserRepository userRepository;

	@Before
	public void setUp() {
	}
	
	@After
	public void clearContext() {
		SecurityUtils.clearContext();
	}
	
	
	@Test
	public void testFindPhenotypesByExperiment() {
		SecurityUtils.setAnonymousUser();
		TraitUomPage page = service.findPhenotypesByExperiment(1L, 0, 50);
		assertEquals(107L, page.getTotalElements());
		assertEquals(page.getNumberOfElements(), 50);
		assertEquals(50, page.getContent().size());
	}
	
	@Test
	public void testCountPhenotypesByExperiment() {
		SecurityUtils.setAnonymousUser();
		int count = service.countPhenotypeByExperimentCount(1L);
		assertEquals(107, count);
	}
	
	@Test
	public void checkNoVisiblePermissionWhenNoAdmin() {
		SecurityUtils.setAnonymousUser();
		TraitUom phenotype = service.findPhenotype(1L);
		assertTrue((phenotype.getUserPermission().getMask() & BasePermission.READ.getMask()) == BasePermission.READ.getMask()); 
		assertFalse(phenotype.isOwner());
	}
	
	@Test
	public void testFindPhenotype()  {
		SecurityUtils.setAnonymousUser();
		TraitUom phenotype = service.findPhenotype(1L);
		assertNotNull("couldn't find phenotype", phenotype);
		assertEquals("uncorrect number of obsUnits",new Long(167),phenotype.getNumberOfObsUnits());
		assertEquals("uncorrect number of studies",new Long(1),phenotype.getNumberOfStudies());
	}
	
	
	@Test
	public void checkVisiblePermissionsWhenAdmin() {
		createTestUser("ROLE_ADMIN");
		TraitUom phenotype = service.findPhenotype(1L);
		assertTrue(phenotype.isOwner());
	}
	
	private void createTestUser(String role) {
		AppUser appUser = new AppUser("test@test.at");
		appUser.setOpenidUser(false);
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		appUser.setPassword(encoder.encodePassword(SecurityUtils.TEST_PASSWORD, null));
		List<Authority> authorities = new ArrayList<Authority>();
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		SimpleGrantedAuthority auth = new SimpleGrantedAuthority(role); 
		grantedAuthorities.add(auth);
		Authority authority = new Authority();
		authority.setAuthority(role);
		authorities.add(authority);
		appUser.setAuthorities(authorities);
		userRepository.save(appUser);
		SecurityUtils.makeActiveUser(SecurityUtils.TEST_USERNAME, SecurityUtils.TEST_PASSWORD,grantedAuthorities);
	}
}
