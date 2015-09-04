package com.thalesgroup.optet.common.data;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigInteger;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import com.thalesgroup.optet.common.Activator;
import com.thalesgroup.optet.common.jaxb.computation.Computation;
import com.thalesgroup.optet.common.jaxb.computation.Computation.TwAttribute;
import com.thalesgroup.optet.common.jaxb.computation.Computation.TwAttribute.Evidence;
import com.thalesgroup.optet.common.jaxb.optet.ObjectFactory;
import com.thalesgroup.optet.common.jaxb.optet.OptetType;
import com.thalesgroup.optet.common.jaxb.optet.OptetType.Audits;
import com.thalesgroup.optet.common.jaxb.optet.OptetType.Metrics;
import com.thalesgroup.optet.common.jaxb.optet.OptetType.TestCoverage;
import com.thalesgroup.optet.common.jaxb.optet.OptetType.TestResults;
import com.thalesgroup.optet.common.jaxb.optet.OptetType.Audits.File;
import com.thalesgroup.optet.common.jaxb.optet.OptetType.Audits.File.Violation;
import com.thalesgroup.optet.common.jaxb.optet.OptetType.Metrics.Metric;
import com.thalesgroup.optet.common.jaxb.optet.OptetType.TestCoverage.Blocks;
import com.thalesgroup.optet.common.jaxb.optet.OptetType.TestCoverage.Classes;
import com.thalesgroup.optet.common.jaxb.optet.OptetType.TestCoverage.Instructions;
import com.thalesgroup.optet.common.jaxb.optet.OptetType.TestCoverage.Lines;
import com.thalesgroup.optet.common.jaxb.optet.OptetType.TestCoverage.Methods;
import com.thalesgroup.optet.common.jaxb.optet.OptetType.TestResults.TestResult;
import com.thalesgroup.optet.common.data.Severity;
import com.thalesgroup.optet.common.views.OptetAuditModel;
import com.thalesgroup.optet.common.views.OptetAuditModelProvider;
import com.thalesgroup.optet.common.views.OptetMetricModel;
import com.thalesgroup.optet.common.views.OptetMetricModelProvider;
import com.thalesgroup.optet.common.views.OptetTestCoverageModel;
import com.thalesgroup.optet.common.views.OptetTestCoverageModelProvider;


public class OptetDataModel implements DataModel  {
	private ObjectFactory objFactory = null;
	private OptetType optetDatas = null;
	private Audits audits = null;
	private Metrics metrics = null;
	private TestCoverage testCoverage= null;
	private TestResults testResults = null;
	private Map<String, Integer> metricRule = null;

	private Map<String, String> expectedMetric = null;
	private Map<String, String> expectedTWAttribute = null;
	private Map<String, Set<String>> MetricForTWAttribute = null;

	private Map<String, Evidence> computationEvidence = null;
	private Map<String, TwAttribute> computationTWAttribute = null;
	private Map<String, Evidence> computationEvidenceID = null;
	private Map<String, TwAttribute> computationTWAttributeID = null;

	private String JMLRACResult;
	private  String JMLCompResult;
	private  String JMLESCResult;
	
	private List<String> JMLProgress;
	
	/** 
	 * Instance 
	 */
	private static DataModel INSTANCE = new OptetDataModel();


	/**
	 * getInstance return the current instance of the OptetDataModel
	 * @return the current OptetDataModel
	 */
	public static DataModel getInstance()
	{	return INSTANCE;
	}


	/**
	 * constructor
	 */
	private OptetDataModel() {
		super();

		objFactory = new ObjectFactory();
		optetDatas = objFactory.createOptetType();
		audits = objFactory.createOptetTypeAudits();
		metrics = objFactory.createOptetTypeMetrics();
		testCoverage = objFactory.createOptetTypeTestCoverage();
		testResults = objFactory.createOptetTypeTestResults();
		metricRule = new HashMap<>();
		expectedMetric = new  HashMap<>();
		expectedTWAttribute = new  HashMap<>();
		MetricForTWAttribute = new HashMap<>();

		computationEvidence = new  HashMap<>();
		computationTWAttribute = new  HashMap<>();
		computationEvidenceID = new  HashMap<>();
		computationTWAttributeID = new  HashMap<>();

		JMLProgress = new ArrayList<String>();
		
		System.out.println("***************************** compute all");
		Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);
		URL fileURL = bundle.getEntry("xsd/TwAttributeComputation.xml");
		java.io.File file = null;
		try {
			file = new  java.io.File(FileLocator.toFileURL(fileURL).getPath());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		JAXBContext jaxbContext;
		Computation computation = null;
		try {
			jaxbContext = JAXBContext.newInstance(Computation.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			computation = (Computation) jaxbUnmarshaller.unmarshal(file);	



		}
		catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		if (computation!=null){

			System.out.println("parse computation file");
			List<TwAttribute> twAttributeList = computation.getTwAttribute();
			for (Iterator iterator = twAttributeList.iterator(); iterator
					.hasNext();) {
				TwAttribute twAttribute = (TwAttribute) iterator.next();
				computationTWAttribute.put(twAttribute.getName(), twAttribute);
				computationTWAttributeID.put(twAttribute.getID(), twAttribute);
				List<Evidence> evidenceList = twAttribute.getEvidence(); 
				for (Iterator iterator2 = evidenceList.iterator(); iterator2
						.hasNext();) {
					Evidence evidence = (Evidence) iterator2.next();
					computationEvidence.put(evidence.getName(), evidence);
					computationEvidenceID.put(evidence.getID(), evidence);
					if (MetricForTWAttribute.get(twAttribute.getName())==null)
					{
						MetricForTWAttribute.put(twAttribute.getName(), new HashSet<String>());
					}
					MetricForTWAttribute.get(twAttribute.getName()).add(evidence.getName());
					System.out.println("add " + twAttribute.getName() + " for " + evidence.getName());
				}

			}

		}




	}

	public String getTWAttributeID(String name){
		return computationTWAttribute.get(name).getID();
	}

	public String getEvidenceID(String name){
		return computationEvidence.get(name).getID();
	}
	public String getTWAttributeName(String ID){
		return computationTWAttributeID.get(ID).getName();
	}

	public String getEvidenceName(String ID){
		return computationEvidenceID.get(ID).getName();
	}

	public  void addExpectedTWAttribute(String twAttribute, String value){
		expectedTWAttribute.put(twAttribute, value);
	}

	public  String getExpectedTWAttribute(String twAttribute){
		return expectedTWAttribute.get(twAttribute);

	}

	public void cleanExpectedTWAttribute(){
		expectedTWAttribute.clear();
	}


	public  void addExpectedMetric(String metric, String value, String twAttribute){
		System.out.println("addExpectedMetric" + metric+ "-" + twAttribute);

		expectedMetric.put(metric + "-" + twAttribute, value);


	}

	public Set<String> getEvidenceForTWAttribute(String twAttribute){
		return 	MetricForTWAttribute.get(twAttribute);
	}

	public  String getExpectedMetric(String metric, String twAttribute){
		System.out.println("getExpectedMetric" + metric+ "-" + twAttribute);
		return expectedMetric.get(metric+ "-" + twAttribute);

	}

	public void cleanExpectedMetric(){
		expectedMetric.clear();
		//MetricForTWAttribute.clear();
	}

	/* (non-Javadoc)
	 * @see com.thalesgroup.optet.common.data.DataModel#addSumTestResult(java.lang.String, int, int, int, int, int, int)
	 */
	@Override
	public void addSumTestResult(String globalTestName, int nbRun, int nbFailed, int nbIgnored, int nbError, int nbOK, int nbUndefined ){
		testResults.setErrors(BigInteger.valueOf(nbError));
		testResults.setName(globalTestName);
		testResults.setFailures(BigInteger.valueOf(nbFailed));
		testResults.setIgnored(BigInteger.valueOf(nbIgnored));
		testResults.setOk(BigInteger.valueOf(nbOK));
		testResults.setUndefined(BigInteger.valueOf(nbUndefined));
		System.out.println(nbRun +  "  " + nbFailed + " " + nbIgnored + " " + nbError + " " + nbOK + " " + nbUndefined);


		double res = ((double)nbOK)/(double)nbRun;
		System.out.println("Unit Test Ratio " + res);
		System.out.println("Unit Test Ratio " + String.valueOf(res));
		OptetDataModel.getInstance().addMetrics("Unit Test Ratio", String.valueOf(res),"Evidence","");

	}

	/* (non-Javadoc)
	 * @see com.thalesgroup.optet.common.data.DataModel#addTestResult(java.lang.String, java.lang.String)
	 */
	@Override
	public void addTestResult(String testName, String result){
		TestResult res = new TestResult();
		res.setName(testName);
		res.setResult(result);
		testResults.getTestResult().add(res);
	}


	/* (non-Javadoc)
	 * @see com.thalesgroup.optet.common.data.DataModel#addClassesTestCoverage(int, int, double)
	 */
	@Override
	public void addClassesTestCoverage(int total, int covered, double pourcentage){
		Classes classes = new Classes();
		classes.setTotalClasses(total);
		classes.setCoveredClasses(covered);
		classes.setPourcentage(pourcentage);
		testCoverage.setClasses(classes);

		OptetTestCoverageModel entry = new OptetTestCoverageModel("Classes", total, covered, pourcentage);
		OptetTestCoverageModelProvider.INSTANCE.addTestCoverageEntry(entry );
	}

	/* (non-Javadoc)
	 * @see com.thalesgroup.optet.common.data.DataModel#addMethodsTestCoverage(int, int, double)
	 */
	@Override
	public void addMethodsTestCoverage(int total, int covered, double pourcentage){

		OptetDataModel.getInstance().addMetrics("Unit Test Coverage", String.valueOf(pourcentage),"Evidence","");

		Methods methods = new Methods();
		methods.setTotalMethods(total);
		methods.setCoveredMethods(covered);
		methods.setPourcentage(pourcentage);
		testCoverage.setMethods(methods);
		OptetTestCoverageModel entry = new OptetTestCoverageModel("Methods", total, covered, pourcentage);
		OptetTestCoverageModelProvider.INSTANCE.addTestCoverageEntry(entry );
	}

	/* (non-Javadoc)
	 * @see com.thalesgroup.optet.common.data.DataModel#addLinesTestCoverage(int, int, double)
	 */
	@Override
	public void addLinesTestCoverage(int total, int covered, double pourcentage){
		Lines lines = new Lines();
		lines.setTotalLines(total);
		lines.setCoveredLines(covered);
		lines.setPourcentage(pourcentage);
		testCoverage.setLines(lines);

		OptetTestCoverageModel entry = new OptetTestCoverageModel("Lines", total, covered, pourcentage);
		OptetTestCoverageModelProvider.INSTANCE.addTestCoverageEntry(entry );

	}

	/* (non-Javadoc)
	 * @see com.thalesgroup.optet.common.data.DataModel#addBlocksTestCoverage(int, int, double)
	 */
	@Override
	public void addBlocksTestCoverage(int total, int covered, double pourcentage){
		Blocks blocks = new Blocks();
		blocks.setTotalBlocks(total);
		blocks.setCoveredBlocks(covered);
		blocks.setPourcentage(pourcentage);
		testCoverage.setBlocks(blocks);
		OptetTestCoverageModel entry = new OptetTestCoverageModel("Blocks", total, covered, pourcentage);
		OptetTestCoverageModelProvider.INSTANCE.addTestCoverageEntry(entry );

	}

	/* (non-Javadoc)
	 * @see com.thalesgroup.optet.common.data.DataModel#addInstructionsTestCoverage(int, int, double)
	 */
	@Override
	public void addInstructionsTestCoverage(int total, int covered, double pourcentage){
		Instructions instructions = new Instructions();
		instructions.setTotalInstructions(total);
		instructions.setCoveredInstructions(covered);
		instructions.setPourcentage(pourcentage);
		testCoverage.setInstructions(instructions);
		OptetTestCoverageModel entry = new OptetTestCoverageModel("Instructions", total, covered, pourcentage);
		OptetTestCoverageModelProvider.INSTANCE.addTestCoverageEntry(entry );

	}


	/* (non-Javadoc)
	 * @see com.thalesgroup.optet.common.data.DataModel#addMetrics(java.lang.String, java.lang.String)
	 */
	@Override
	public void addMetrics (String metric, String value, String type, String category)
	{
		//Activator.getDefault().logInfo("add metric " + metric + " with value " + value);
		System.out.println("add metric " + metric + " with value " + value);
		Metric _metric = objFactory.createOptetTypeMetricsMetric();
		_metric.setName(metric);

		NumberFormat formatFR = NumberFormat.getInstance(Locale.FRENCH);
		NumberFormat formatUS = NumberFormat.getInstance(Locale.US);
		if (value!=null){
			Double val = null;
			try {
				if (value.contains(",")){
					val = formatFR.parse(value).doubleValue()*100;
				}else if (value.contains(".")){
					val = formatUS.parse(value).doubleValue()*100;
				}else{
					val = formatFR.parse(value).doubleValue()*100;
				}
					
				System.out.println("value " + val);
				_metric.setResult(String.valueOf(val));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//			if (value!=null)
			//				_metric.setResult(value.replace('.', ','));
		}
		_metric.setType(type);
		_metric.setCategory(category);

		metrics.getMetric().add(_metric);

		OptetMetricModel entry = new OptetMetricModel(metric, value, type);
		OptetMetricModelProvider.INSTANCE.addAuditEntry(metric, entry);


	}

	/* (non-Javadoc)
	 * @see com.thalesgroup.optet.common.data.DataModel#addError(org.eclipse.core.resources.IFile, java.lang.String, int, com.thalesgroup.optet.common.data.Severity, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void addError(IFile file, String tool, int line, Severity severity, String ruleset, String message, String recommandation, String source)
	{

		//Activator.getDefault().logInfo("file" + file + "  line" + line + "   message" + message);

		File f = objFactory.createOptetTypeAuditsFile();
		f.setName(file.getLocation().toOSString());
		Violation e = objFactory.createOptetTypeAuditsFileViolation();
		e.setTool(tool);
		e.setLine(line);
		e.setSeverity(severity.name());
		e.setRuleset(ruleset);
		e.setMessage(message);
		e.setRecommandation(recommandation);
		e.setSource(source);

		f.getViolation().add(e);
		audits.getFile().add(f);
		OptetAuditModel entry = new OptetAuditModel(tool, file, line, severity.name(), ruleset, message, recommandation, source);
		OptetAuditModelProvider.INSTANCE.addAuditEntry(entry );

	}

	/* (non-Javadoc)
	 * @see com.thalesgroup.optet.common.data.DataModel#generateXML()
	 */
	@Override
	public void generateXML() throws JAXBException
	{
		Activator.getDefault().logInfo("generate xml");
		JAXBContext jaxbContext = JAXBContext.newInstance(OptetType.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		Activator.getDefault().logInfo("generate xml");

		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		Activator.getDefault().logInfo("generate xml setAudits");
		optetDatas.setAudits(audits);
		Activator.getDefault().logInfo("generate xml setMetrics");
		optetDatas.setMetrics(metrics);
		Activator.getDefault().logInfo("generate xml testCoverage");
		optetDatas.setTestCoverage(testCoverage);
		Activator.getDefault().logInfo("generate xml testResult");
		optetDatas.setTestResults(testResults);
		String format = "dd/MM/yy H:mm:ss";

		java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat( format );
		java.util.Date date = new java.util.Date();
		Activator.getDefault().logInfo("generate xml setVersion");

		optetDatas.setVersion(formater.format( date ));
		jaxbMarshaller.marshal(optetDatas, System.out);

	}


	/* (non-Javadoc)
	 * @see com.thalesgroup.optet.common.data.DataModel#cleanAudit()
	 */
	@Override
	public void cleanAudit() {
		OptetAuditModelProvider.INSTANCE.cleanEntries();
		audits.getFile().clear();
	}

	/* (non-Javadoc)
	 * @see com.thalesgroup.optet.common.data.DataModel#cleanMetric()
	 */
	@Override
	public void cleanMetric() {
		System.out.println("CLEAN METRIC");
		OptetMetricModelProvider.INSTANCE.cleanEntries();
		metrics.getMetric().clear();
		metricRule.clear();
	}

	/* (non-Javadoc)
	 * @see com.thalesgroup.optet.common.data.DataModel#cleanTestCoverage()
	 */
	@Override
	public void cleanTestCoverage(){
		OptetTestCoverageModelProvider.INSTANCE.cleanEntries();
		testCoverage.setBlocks(null);
		testCoverage.setClasses(null);
		testCoverage.setInstructions(null);
		testCoverage.setLines(null);
		testCoverage.setMethods(null);
	}

	private static String pourcentage(int a,int b){
		double c = new Double(b);

		double resultat = a/c;
		double resultatFinal = resultat*100;


		DecimalFormat df = new DecimalFormat("###.##");
		return df.format(resultatFinal) + " %";
	}


	/* (non-Javadoc)
	 * @see com.thalesgroup.optet.common.data.DataModel#computeMetric()
	 */
	@Override
	public void computeRulesMetric() {

		// TODO Auto-generated method stub
		List<OptetAuditModel> auditRes = OptetAuditModelProvider.INSTANCE.getAudit();
		Map<String, Set<String>> mapRes = new HashMap<>();
		for (Iterator iterator = auditRes.iterator(); iterator.hasNext();) {
			OptetAuditModel optetAuditModel = (OptetAuditModel) iterator.next();
			if (mapRes.containsKey(optetAuditModel.getRuleset())){
				mapRes.get(optetAuditModel.getRuleset()).add(optetAuditModel.getSource());
			}else{
				mapRes.put(optetAuditModel.getRuleset(), new HashSet<String>());
				
				mapRes.get(optetAuditModel.getRuleset()).add(optetAuditModel.getSource());
			}
			System.out.println("add " + optetAuditModel.getRuleset() + "from tool " + optetAuditModel.getTool() + " with message = " + optetAuditModel.getSource());

		}

		for(Entry<String, Set<String>> entry : mapRes.entrySet()) {
			String cle = entry.getKey();
			

			System.out.println("cle " + cle);
			Set<String> valeur = entry.getValue();
			for (Iterator iterator = valeur.iterator(); iterator.hasNext();) {
				String string = (String) iterator.next();
				System.out.println("value " + string);
			}
			
			
			System.out.println("value size " + valeur.size());
			System.out.println("total rules " + metricRule.get(cle));
			double res = ((double)(metricRule.get(cle))-(double)(valeur.size()))/(double)(metricRule.get(cle));
			System.out.println(" resr " + res);
			OptetDataModel.getInstance().addMetrics(cle, String.valueOf(res),"Evidence","");

		}	
	}

	/**
	 * @param valeurList
	 * @param name
	 * @return
	 */
	private boolean search(List<String> valeurList, String name) {
		// TODO Auto-generated method stub
		for (Iterator iterator = valeurList.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			if (string.equals(name))
				return true;
		}

		return false;
	}


	public  String getMetric(String metric){
		List<OptetMetricModel> listMetric = OptetMetricModelProvider.INSTANCE.getAudit();
		for (Iterator iterator = listMetric.iterator(); iterator.hasNext();) {
			OptetMetricModel optetMetricModel = (OptetMetricModel) iterator
					.next();

			//System.out.println("optetMetricModel.getMetric() " + optetMetricModel.getMetric() + " equals with " + metric);
			if (optetMetricModel.getMetric().equals(metric)){
				return optetMetricModel.getValue();
			}

		}
		return null;
	}


	public  List<Metric> getMetrics(){
		return metrics.getMetric();
	}


	private static double pourcentageDouble(int a, int b){
		double c = new Double(b);

		double resultat = a/c;
		double resultatFinal = resultat*100;
		return resultatFinal;
	}

	private static String pourcentageString(int a,int b){
		double c = new Double(b);

		double resultat = a/c;
		double resultatFinal = resultat*100;


		DecimalFormat df = new DecimalFormat("###.##");
		return df.format(resultatFinal) + " %";
	}

	/* (non-Javadoc)
	 * @see com.thalesgroup.optet.common.data.DataModel#configureRulesMetric(java.lang.String, int)
	 */
	@Override
	public void configureRulesMetric(String metric, int nbrules) {
		// TODO Auto-generated method stub
		System.out.println("add metric " + metric + " with " + nbrules +" rules");
		if (metricRule.containsKey(metric)){
			metricRule.put(metric, metricRule.get(metric)+nbrules);
		}else{
			metricRule.put(metric, nbrules);
		}		
	}



	/* (non-Javadoc)
	 * @see com.thalesgroup.optet.common.data.DataModel#computeMetric()
	 */
	@Override
	public void computeMetric() {
		// TODO Auto-generated method stub

		System.out.println("computeMetric");
		Set<String> exTWAttr = expectedTWAttribute.keySet();
		for (Iterator iterator = exTWAttr.iterator(); iterator.hasNext();) {
			String attr = (String) iterator.next();
			System.out.println("compute expectedTWAttribute" + attr);
			computeTWAttribute(attr);
		}
	}

	@Override
	public String computeTWAttribute(String twAttributeName){
		String result = null;

		TwAttribute twAttribute = computationTWAttribute.get(twAttributeName);

		double totalWeight = 0;
		double totalValue = 0;
		List<Evidence> evidenceList = twAttribute.getEvidence(); 
		for (Iterator iterator2 = evidenceList.iterator(); iterator2
				.hasNext();) {
			Evidence evidence = (Evidence) iterator2.next();
			NumberFormat formatFR = NumberFormat.getInstance(Locale.FRENCH);
			NumberFormat formatUS = NumberFormat.getInstance(Locale.US);
			//format.parse("1.233").doubleValue();


			String res = OptetDataModel.getInstance().getMetric(evidence.getName());
			if (res != null){
				System.out.println("*        *             *      Evidence : " +  evidence.getName() + " value : " + res);
				double resDouble=0;
				try {
					if (res.contains(","))
						resDouble = formatFR.parse(res).doubleValue();
					else if (res.contains("."))
						resDouble = formatUS.parse(res).doubleValue();
					else
						resDouble = formatFR.parse(res).doubleValue();
						
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				totalValue = totalValue + resDouble*evidence.getWeigth();
				totalWeight = totalWeight + evidence.getWeigth();
			}else
			{
				OptetDataModel.getInstance().addMetrics(twAttribute.getName(),null, "TwAttribute", null);
				return null;
			}
		}
		if (!Double.isNaN(totalValue/totalWeight)){
			System.out.println("*        *             *      TwAttribute : " + twAttribute.getName() + " value : " + String.valueOf(totalValue/totalWeight));
			result = String.valueOf((totalValue/totalWeight)).replace('.', ',');
			OptetDataModel.getInstance().addMetrics(twAttribute.getName(),result, "TwAttribute", null);
			result = String.valueOf((totalValue/totalWeight)*100).replace('.', ',');
		}

		return result;
	}

	//	private void computeinternaleMetric() {
	//		// TODO Auto-generated method stub
	//
	//
	//
	//
	//		List<TwAttribute> twAttributeList = computation.getTwAttribute();
	//		for (Iterator iterator = twAttributeList.iterator(); iterator
	//				.hasNext();) {
	//			TwAttribute twAttribute = (TwAttribute) iterator.next();
	//			double totalWeight = 0;
	//			double totalValue = 0;
	//			List<Evidence> evidenceList = twAttribute.getEvidence(); 
	//			for (Iterator iterator2 = evidenceList.iterator(); iterator2
	//					.hasNext();) {
	//				Evidence evidence = (Evidence) iterator2.next();
	//
	//
	//
	//				String res = OptetDataModel.getInstance().getMetric(evidence.getName());
	//				if (res != null){
	//					System.out.println("*        *             *      Evidence : " +  evidence.getName() + " value : " + res);
	//					totalValue = totalValue + Double.parseDouble(res)*evidence.getWeigth();
	//					totalWeight = totalWeight + evidence.getWeigth();
	//				}
	//			}
	//			if (!Double.isNaN(totalValue/totalWeight)){
	//				System.out.println("*        *             *      TwAttribute : " + twAttribute.getName() + " value : " + String.valueOf(totalValue/totalWeight));
	//				OptetDataModel.getInstance().addMetrics(twAttribute.getName(),String.valueOf(totalValue/totalWeight), "TwAttribute", null);
	//			}
	//		}
	//	}

	public Set<String> getAllTWAttributeName(){
		return computationTWAttribute.keySet();
	}
	
	
	
	public  void setJMLRACResult(String res){
		this.JMLRACResult = res;
	}
	public  void setJMLCompResult(String res){
		this.JMLCompResult = res;
	}
	public  void setJMLESCResult(String res){
		this.JMLESCResult = res;
	}
	
	public  String getJMLRACResult(){
		return JMLRACResult;
	}
	public  String getJMLCompResult(){
		return JMLCompResult;
	}
	public  String getJMLESCResult(){
		return JMLESCResult;
	}
	
	
	public  List<String> getJMLProgress(){
		return JMLProgress;
	}
	public  void resetJMLProgress(){
		JMLProgress.clear();
	}
	public  void addJMLProgress(String progress){
		JMLProgress.add(progress);
	}

	
}
