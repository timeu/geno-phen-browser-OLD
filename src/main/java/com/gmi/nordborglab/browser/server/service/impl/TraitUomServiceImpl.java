package com.gmi.nordborglab.browser.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmi.nordborglab.browser.server.domain.observation.Experiment;
import com.gmi.nordborglab.browser.server.domain.pages.TraitUomPage;
import com.gmi.nordborglab.browser.server.domain.phenotype.TraitUom;
import com.gmi.nordborglab.browser.server.repository.ExperimentRepository;
import com.gmi.nordborglab.browser.server.repository.TraitUomRepository;
import com.gmi.nordborglab.browser.server.security.CustomAccessControlEntry;
import com.gmi.nordborglab.browser.server.security.SecurityUtil;
import com.gmi.nordborglab.browser.server.service.TraitUomService;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;

@Service
@Transactional(readOnly = true)
public class TraitUomServiceImpl implements TraitUomService {
	
	@Resource
	private TraitUomRepository traitUomRepository;
	@Resource
	private ExperimentRepository experimentRepository;

	@Resource
	private MutableAclService aclService;
	
	@Resource
	private RoleHierarchy roleHierarchy;

	@Override
	public TraitUomPage findPhenotypesByExperiment(Long id, int start, int size) {
		TraitUomPage page = null;
		PageRequest pageRequest = new PageRequest(start, size);
		FluentIterable<TraitUom> traits = findPhenotypesByExperimentid(id);
		int	totalElements = traits.size();
		int pageStart = 0;
		if (start > 0)
			pageStart = start/size;
		if (totalElements > 0) {
			page = new TraitUomPage(Iterables.get(Iterables.partition(traits, size), pageStart), pageRequest,
					totalElements);
		}
		else {
			page = new TraitUomPage(traits.toImmutableList().asList(), pageRequest, 0);
		}
		return page;
	}
	
	private FluentIterable<TraitUom> findPhenotypesByExperimentid(Long id) {
		final List<Sid> authorities = SecurityUtil.getSids(roleHierarchy);
		final ImmutableList<Permission> permissions = ImmutableList.of(BasePermission.READ);
		FluentIterable<TraitUom> traits = FluentIterable.from(traitUomRepository.findByExperimentId(id));
		if (traits.size() > 0) {
			final ImmutableBiMap<TraitUom,ObjectIdentity> identities = SecurityUtil.retrieveObjectIdentites(traits.toImmutableList()).inverse();
			final ImmutableMap<ObjectIdentity,Acl> acls = ImmutableMap.copyOf(aclService.readAclsById(identities.values().asList(), authorities));
			
			Predicate<TraitUom> predicate = new Predicate<TraitUom>() {

				@Override
				public boolean apply(TraitUom trait) {
					boolean flag = false;
					ObjectIdentity identity = identities.get(trait);
					if (acls.containsKey(identity)) {
						Acl acl = acls.get(identity);
						if (acl.isGranted(permissions, authorities, false)) 
							flag = true;
					}
					return flag;
				}
			};
			traits = traits.filter(predicate);
		}
		return traits;
	}

	///TODO Custom query for better performance
	@Override
	public int countPhenotypeByExperimentCount(Long id) {
		FluentIterable<TraitUom> traits = findPhenotypesByExperimentid(id);
		return traits.size();
	}

	@Override
	public TraitUom findPhenotype(Long id) {
		TraitUom traitUom = traitUomRepository.findOne(id);
		traitUom = setPermissionAndOwner(traitUom);
		return traitUom;
	}

	@Override
	@Transactional(readOnly = false)
	public TraitUom save(TraitUom trait) {
		if (trait.getId() == null)
			throw new RuntimeException("use create method for adding new traits");
		trait = traitUomRepository.save(trait);
//		if (trait.getId() == null) {
//			CumulativePermission permission = new CumulativePermission();
//			permission.set(BasePermission.ADMINISTRATION);
//			addPermission(experiment, new PrincipalSid(SecurityUtil.getUsername()),
//				permission);
//		}
		trait = setPermissionAndOwner(trait);
		return trait;
	}

	private TraitUom setPermissionAndOwner(TraitUom traitUom) {
		List<Sid> authorities = SecurityUtil.getSids(roleHierarchy);
		ObjectIdentity oid = new ObjectIdentityImpl(Experiment.class,
				traitUom.getId());
		Acl acl = aclService.readAclById(oid, authorities);
		boolean isOwner = false;
		for (Sid sid : authorities) {
			if (sid.equals(acl.getOwner())) {
				isOwner = true;
				break;
			}
		}
		for (AccessControlEntry ace: acl.getEntries()) {
			if (authorities.contains(ace.getSid())) {
				traitUom.setUserPermission(new CustomAccessControlEntry((Long)ace.getId(),ace.getPermission().getMask(),ace.isGranting()));
				break;
			}
		}
		traitUom.setIsOwner(isOwner);
		traitUom.setNumberOfObsUnits(traitUomRepository.countObsUnitsByPhenotypeId(traitUom.getId()));
		traitUom.setNumberOfStudies(traitUomRepository.countStudiesByPhenotypeId(traitUom.getId()));
		return traitUom;
	}
}

