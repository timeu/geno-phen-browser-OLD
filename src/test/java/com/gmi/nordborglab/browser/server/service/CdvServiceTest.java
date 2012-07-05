package com.gmi.nordborglab.browser.server.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.gmi.nordborglab.browser.server.domain.cdv.Study;
import com.gmi.nordborglab.browser.server.domain.observation.Experiment;
import com.gmi.nordborglab.browser.server.domain.pages.StudyPage;
import com.gmi.nordborglab.browser.server.testutils.BaseTest;
import com.gmi.nordborglab.browser.server.testutils.SecurityUtils;
import com.google.common.collect.ImmutableList;

public class CdvServiceTest extends BaseTest {

	@Resource
	private CdvService service;


	@Resource
	private MutableAclService aclService;

	@Before
	public void setUp() {
	}

	@After
	public void clearContext() {
		SecurityUtils.clearContext();
	}

	@Test
	public void testFindStudiesByPhenotypeId() {
		SecurityUtils.setAnonymousUser();
		StudyPage page = service.findStudiesByPhenotypeId(1L, 0, 50);
		assertEquals(1, page.getTotalElements());
	}
	
	@Test
	public void checkNoVisiblePermissionWhenNoAdmin() {
		SecurityUtils.setAnonymousUser();
		Study study = service.findStudy(1L);
		assertTrue((study.getUserPermission().getMask() & BasePermission.READ.getMask()) == BasePermission.READ.getMask()); 
		assertFalse(study.isOwner());
	}
	
	@Test
	public void testFindPhenotype()  {
		SecurityUtils.setAnonymousUser();
		Study study = service.findStudy(1L);
		assertNotNull("couldn't find phenotype", study);
	}
	
	
	@Test
	public void checkVisiblePermissionsWhenAdmin() {
		Collection<? extends GrantedAuthority> adminAuthorities = ImmutableList.of(new SimpleGrantedAuthority("ROLE_ADMIN")).asList();
	    SecurityUtils.makeActiveUser("TEST", "TEST",adminAuthorities);
	    Study study = service.findStudy(1L);
		assertTrue(study.isOwner());
	}

	@Test(expected=AccessDeniedException.class)
	public void testFindStudiesByPhenotypeIdAccessedDenied() {
		Collection<? extends GrantedAuthority> adminAuthorities = ImmutableList.of(new SimpleGrantedAuthority("ROLE_ADMIN")).asList();
	    SecurityUtils.makeActiveUser("TEST", "TEST",adminAuthorities);
		ObjectIdentity oid = new ObjectIdentityImpl(Experiment.class,1L);
		List<Sid> authorities = Collections.singletonList((Sid)new GrantedAuthoritySid("ROLE_ANONYMOUS"));
		MutableAcl acl = (MutableAcl)aclService.readAclById(oid, authorities);
		
		for (int i=0;i<acl.getEntries().size();i++) {
			if (acl.getEntries().get(i).getSid().equals(authorities.get(0)))
			{
				acl.deleteAce(i);
				break;
			}
		}
		aclService.updateAcl(acl);
		SecurityUtils.setAnonymousUser();
		service.findStudiesByPhenotypeId(1L, 0, 50);
	}
}
