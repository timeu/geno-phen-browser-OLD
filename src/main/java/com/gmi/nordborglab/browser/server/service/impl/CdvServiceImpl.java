package com.gmi.nordborglab.browser.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmi.nordborglab.browser.server.domain.cdv.Study;
import com.gmi.nordborglab.browser.server.domain.pages.StudyPage;
import com.gmi.nordborglab.browser.server.domain.phenotype.TraitUom;
import com.gmi.nordborglab.browser.server.repository.StudyRepository;
import com.gmi.nordborglab.browser.server.security.CustomAccessControlEntry;
import com.gmi.nordborglab.browser.server.security.SecurityUtil;
import com.gmi.nordborglab.browser.server.service.CdvService;
import com.google.common.collect.ImmutableList;

@Service
@Transactional(readOnly = true)
public class CdvServiceImpl implements CdvService {

	@Resource
	protected StudyRepository studyRepository;

	@Resource
	protected RoleHierarchy roleHierarchy;
	
	@Resource
	protected MutableAclService aclService;

	
	@Override
	public StudyPage findStudiesByPhenotypeId(Long id, int start, int size) {
		final List<Sid> authorities = SecurityUtil.getSids(roleHierarchy);
		final ImmutableList<Permission> permissions = ImmutableList
				.of(BasePermission.READ);
		ObjectIdentity oid = new ObjectIdentityImpl(TraitUom.class,id);
		Acl acl = aclService.readAclById(oid, authorities);
		try {
			if (!acl.isGranted(permissions, authorities, false)) 
				throw new AccessDeniedException("not allowed");
		}
		catch (NotFoundException e) {
			throw new AccessDeniedException("not allowed");
		}
		StudyPage page = null;
		if (start > 0)
			start = start/size;
		PageRequest pageRequest = new PageRequest(start, size);
		Page<Study> studyPage = studyRepository.findByPhenotypeId(id,
				pageRequest);
		page = new StudyPage(studyPage.getContent(), pageRequest,
				studyPage.getTotalElements());
		return page;
	}


	@Override
	public Study findStudy(Long id) {
		Study study = studyRepository.findOne(id);
		///TODO add ACL to Study derived from Trait 
		TraitUom trait = study.getTraits().get(0).getTraitUom(); 
		final List<Sid> authorities = SecurityUtil.getSids(roleHierarchy);
		final ImmutableList<Permission> permissions = ImmutableList
				.of(BasePermission.READ);
		ObjectIdentity oid = new ObjectIdentityImpl(TraitUom.class,trait.getId());
		Acl acl = aclService.readAclById(oid, authorities);
		try {
			if (!acl.isGranted(permissions, authorities, false)) 
				throw new AccessDeniedException("not allowed");
		}
		catch (NotFoundException e) {
			throw new AccessDeniedException("not allowed");
		}
		boolean isOwner = false;
		for (Sid sid : authorities) {
			if (sid.equals(acl.getOwner())) {
				isOwner = true;
				break;
			}
		}
		AccessControlEntry ace = null;
		if (acl.getEntries().size() > 0)
			 ace = acl.getEntries().get(0);
		else if (acl.getParentAcl().getEntries().size() > 0)
			ace = acl.getParentAcl().getEntries().get(0);
			
		study.setIsOwner(isOwner);
		if (ace != null)
			study.setUserPermission(new CustomAccessControlEntry(ace.getPermission().getMask(),ace.isGranting()));
		return study;
	}

}
