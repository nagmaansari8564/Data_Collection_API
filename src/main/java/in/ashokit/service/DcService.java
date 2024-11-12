package in.ashokit.service;

import java.util.List;
import java.util.Map;

import in.ashokit.binding.Child;
import in.ashokit.binding.ChildRequest;
import in.ashokit.binding.DcSummary;
import in.ashokit.binding.Education;
import in.ashokit.binding.Income;
import in.ashokit.binding.PlanSelection;

public interface DcService {

	public Long loadCaseNum(Integer appId);

	public Map<Integer,String> getPlanNames();

	public Long savePlanSelection(PlanSelection ps);

	public Long saveIncomeData(Income inc);

	public Long saveEducation(Education edu);

	public Long saveChildrens(ChildRequest request);

	public DcSummary getSummary(Long caseNumber);

}
