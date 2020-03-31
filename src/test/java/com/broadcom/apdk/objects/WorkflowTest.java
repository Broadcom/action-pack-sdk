package com.broadcom.apdk.objects;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.xmlunit.matchers.CompareMatcher;

public class WorkflowTest {
	
	@Test
	public void testJOBP() {
		String expectedXML = XmlHelper.getXMLFragmentAsString("JOBP.TEST.PLAIN.xml", "uc-export/JOBP");
		String testXML = XmlHelper.getXMLAsString(new Workflow("JOBP.TEST.PLAIN"));		
		assertThat(testXML, CompareMatcher.isIdenticalTo(expectedXML));
	} 
	
	@Test
	public void testJOBS_Windows() {
		String expectedXML = XmlHelper.getXMLFragmentAsString("JOBS.WINDOWS.TEST.PLAIN.xml", "uc-export/JOBS_WINDOWS");
		String testXML = XmlHelper.getXMLAsString(new JobWindows("JOBS.WINDOWS.TEST.PLAIN"));			
		assertThat(testXML, CompareMatcher.isIdenticalTo(expectedXML));
	} 
	
	@Test
	public void testJOBS_Unix() {
		String expectedXML = XmlHelper.getXMLFragmentAsString("JOBS.UNIX.TEST.PLAIN.xml", "uc-export/JOBS_UNIX");
		String testXML = XmlHelper.getXMLAsString(new JobUnix("JOBS.UNIX.TEST.PLAIN"));	
		assertThat(testXML, CompareMatcher.isIdenticalTo(expectedXML));
	} 
	
	@Test
	public void testJOBP_Action() {
		// Create ITPA_SHARED PromptSet for overwriting the Agent information
		PromptSet overwriteAgentPromptSet = getOverwriteAgentPromptSet();		
		
		// Create empty PromptSet for input parameters
		PromptSet emptyPromptSet = new PromptSet("PCK.CUSTOM_SAMPLE.PRV.PROMPTSET.TEST_ACTION");
		emptyPromptSet.setTitle("Input");
		
		// Create Workflow
		Workflow action = new Workflow("PCK.CUSTOM_SAMPLE.PUB.ACTION.TEST_ACTION");
		action.setTitle("TEST_ACTION");
		action.setChildQueue("*OWN");
		action.setErrorFreeStatus("ANY_OK");
		action.setGenerateAtRuntime(true);
		
		List<IPromptSet> promptSets = new ArrayList<IPromptSet>();
		promptSets.add(emptyPromptSet);
		promptSets.add(overwriteAgentPromptSet);
		action.setPromptSets(promptSets);
		 
		WorkflowStartTask start = new WorkflowStartTask(2);		
		List<WorkflowTaskPredecessor> predecessors = new ArrayList<WorkflowTaskPredecessor>();
		predecessors.add(new WorkflowTaskPredecessor(1, "ANY_OK", 1));
		WorkflowTask jobWIN = new WorkflowTask(2, "PCK.CUSTOM_SAMPLE.PRV.JOB.TEST_ACTION@WINDOWS",
				"JOBS", 1, 2, predecessors);
		jobWIN.setStatusMismatchOrTimeFailure(StatusMismatchOrTimeFailure.ABORT_TASK);
		WorkflowTask jobUNIX = new WorkflowTask(3, "PCK.CUSTOM_SAMPLE.PRV.JOB.TEST_ACTION@UNIX",
				"JOBS", 3, 2, predecessors);
		jobUNIX.setStatusMismatchOrTimeFailure(StatusMismatchOrTimeFailure.ABORT_TASK);
		List<WorkflowTaskPredecessor> endPredecessors = new ArrayList<WorkflowTaskPredecessor>();
		endPredecessors.add(new WorkflowTaskPredecessor(1, "ANY_OK", 3));
		endPredecessors.add(new WorkflowTaskPredecessor(2, "ANY_OK", 2));
		WorkflowEndTask end = new WorkflowEndTask(4, 2, 3, endPredecessors);
		end.setStatusMismatchOrTimeFailure(StatusMismatchOrTimeFailure.ABORT_TASK);
		
		List<IWorkflowTask> tasks = new ArrayList<IWorkflowTask>();
		tasks.add(start);
		tasks.add(jobWIN);
		tasks.add(jobUNIX);
		tasks.add(end);
		action.setTasks(tasks);
		
		String expectedXML = XmlHelper.getXMLFragmentAsString(
				"PCK.CUSTOM_SAMPLE.PUB.ACTION.TEST_ACTION.xml", "uc-export/JOBP");
		String testXML = XmlHelper.getXMLAsString(action);
		
		assertThat(testXML, CompareMatcher.isIdenticalTo(expectedXML));
	}
	
	private PromptSet getOverwriteAgentPromptSet() {
		PromptSet overwriteAgentPromptSet = new PromptSet("PCK.ITPA_SHARED.PRV.PROMPTSET.OVERWRITE_AGENT");
		overwriteAgentPromptSet.setTitle("Overwrite Agent");
		
		IPrompt<String> overwriteAgentCombo = new PromptCombo(
				"&AGENT#", 
				"Overwrite Agent",
				"Overwrites the default agent",
				"STATIC",
				"PCK.ITPA_SHARED.PRV.VARA.AGENTS");
		IPrompt<String> overwriteAgentLoginCombo = new PromptCombo(
				"&LOGIN#",
				"Overwrite Login",
				"Overwrites the default login",
				"STATIC",
				"PCK.ITPA_SHARED.PRV.VARA.LOGINS");
		
		List<IPrompt<?>> prompts = new ArrayList<IPrompt<?>>();
		prompts.add(overwriteAgentCombo);
		prompts.add(overwriteAgentLoginCombo);
		overwriteAgentPromptSet.setPrompts(prompts, "Overwrite Settings");
		return overwriteAgentPromptSet;
	}

}
