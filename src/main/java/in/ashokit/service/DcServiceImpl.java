package in.ashokit.service;

import java.beans.Beans;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import ch.qos.logback.classic.pattern.Util;
import in.ashokit.binding.Child;
import in.ashokit.binding.ChildRequest;
import in.ashokit.binding.DcSummary;
import in.ashokit.binding.Education;
import in.ashokit.binding.Income;
import in.ashokit.binding.PlanSelection;
import in.ashokit.entity.CitizenAppEntity;
import in.ashokit.entity.DcCaseEntity;
import in.ashokit.entity.DcChildren;
import in.ashokit.entity.DcEducation;
import in.ashokit.entity.DcIncomeEntity;
import in.ashokit.entity.PlanEntity;
import in.ashokit.repo.CitizenAppRepo;
import in.ashokit.repo.DcCaseRepo;
import in.ashokit.repo.DcChildrenRepo;
import in.ashokit.repo.DcEducationRepo;
import in.ashokit.repo.DcIncomeRepo;
import in.ashokit.repo.PlanRepo;

@Service
public class DcServiceImpl implements DcService {

	@Autowired
	private DcCaseRepo dcCaseRepo;

	@Autowired
	private PlanRepo planRepo;

	@Autowired
	private DcIncomeRepo dcIncomeRepo;

	@Autowired
	private DcEducationRepo dcEducationRepo;

	@Autowired
	private DcChildrenRepo dcChildrenRepo;

	@Autowired
	private CitizenAppRepo citizenAppRepo;

	@Override
	public Long loadCaseNum(Integer appId) {

		Optional<CitizenAppEntity> app = citizenAppRepo.findById(appId);

		if (app.isPresent()) {
			DcCaseEntity dcCaseEntity = new DcCaseEntity();
			dcCaseEntity.setAppId(appId);
			dcCaseEntity = dcCaseRepo.save(dcCaseEntity);
			return dcCaseEntity.getCaseNum();
		}
		return 0L;


	}

	@Override
	public Map<Integer,String> getPlanNames() {
		  List<PlanEntity> findAll = planRepo.findAll();
		Map<Integer, String> hashMap = new HashMap<>();

		for (PlanEntity entity : findAll) {
			hashMap.put(entity.getPlanId(), entity.getPlanName());
		}

		return hashMap;
	}

	@Override
	public Long savePlanSelection(PlanSelection planSelection) {

	    Optional<DcCaseEntity> byId = dcCaseRepo.findById(planSelection.getCaseNum());
	    if (byId.isPresent()){
			DcCaseEntity dcCaseEntity = byId.get();
			dcCaseEntity.setPlanId(planSelection.getPlaneId());
			dcCaseRepo.save(dcCaseEntity);
			return planSelection.getCaseNum();
			
		}

		return null;
	}

	@Override
	public Long saveIncomeData(Income inc) {

		DcIncomeEntity dcIncomeEntity = new DcIncomeEntity();
		BeanUtils.copyProperties(inc, dcIncomeEntity);
		dcIncomeRepo.save(dcIncomeEntity);

		return inc.getCaseNum();

	}

	@Override
	public Long saveEducation(Education edu) {
		DcEducation dcEducation = new DcEducation();
		BeanUtils.copyProperties(edu, dcEducation);
		dcEducationRepo.save(dcEducation);
		return edu.getCaseNum();
	}

	@Override
	public Long saveChildrens(ChildRequest request) {
		
           List<Child> childs = request.getChilds();
		for (Child c : childs) {
			DcChildren dcChildren = new DcChildren();
			BeanUtils.copyProperties(c, dcChildren);
			dcChildrenRepo.save(dcChildren);
		}

		return request.getCaseNum() ;
	}

	@Override
	public DcSummary getSummary(Long caseNumber) {

		String planName = "";
		DcIncomeEntity incomeEntity = dcIncomeRepo.findByCaseNum(caseNumber);
		DcEducation educationEntity = dcEducationRepo.findByCaseNum(caseNumber);
		List<DcChildren> childEntity = dcChildrenRepo.findByCaseNum(caseNumber);
		Optional<DcCaseEntity> dcCase = dcCaseRepo.findById(caseNumber);
		if (dcCase != null) {
			Integer planId = dcCase.get().getPlanId();
			Optional<PlanEntity> plan = planRepo.findById(planId);
			if (plan != null) {
				planName = plan.get().getPlanName();
			}
		}

		DcSummary summary = new DcSummary();
		summary.setPlanName(planName);

		Income income = new Income();
		BeanUtils.copyProperties(incomeEntity, income);
		summary.setIncome(income);

		Education education = new Education();
		BeanUtils.copyProperties(educationEntity, education);
		summary.setEducation(education);

		List<Child> childs = new ArrayList<Child>();
		for (DcChildren dcChildren : childEntity) {
			Child child = new Child();
			BeanUtils.copyProperties(dcChildren, child);
			childs.add(child);
		}
		summary.setChilds(childs);

		return summary;
	}

}
