package ie.cct.model;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Measure implements MeasurementSystem {
	
	MeasurementSystem.measurmentLocalSystem measurmentLocalSystem = null; // Check interface for details
	MeasurementSystem.measurementType measurementType = null; // Check interface for details
	
	private static double ounceToGram = 28.35; // 1 ounce = 28.35 gr
	private static double fluidOunceToML = 29.57; // 1 fluid ounce = 29.57 ml
	
	private static List<String> measurementUnitUsVolType = Arrays.asList("tsp", "Tbsp", "flOz", "cup", "pint");
	private static List<Double> measurementUnitUsVolInFlOz = Arrays.asList(0.17, 0.5, 1.0, 8.0, 16.0); // flOz is taken as reference unit
	
	private static List<HashMap> measurementUnitUsVol = buildMeasHashMap(measurementUnitUsVolType, measurementUnitUsVolInFlOz);

	private static List<String> measurementUnitUsWeightType = Arrays.asList("oz", "lb");
	private static List<Double> measurementUnitUsWeightInOz = Arrays.asList(1.0, 16.0); // Oz is taken as reference unit
	
	private static List<HashMap> measurementUnitUsWeight = buildMeasHashMap(measurementUnitUsWeightType, measurementUnitUsWeightInOz);
	
	private static List<String> measurementUnitMetrVolType = Arrays.asList("ml", "lt");
	private static List<Double> measurementUnitMetrVolInMl = Arrays.asList(1.0, 1000.0); // ml is taken as reference unit
	
	private static List<HashMap> measurementUnitMetrVol = buildMeasHashMap(measurementUnitMetrVolType, measurementUnitMetrVolInMl);
	
	private static List<String> measurementUnitMetrWeightType = Arrays.asList("gr", "kg");
	private static List<Double> measurementUnitMetrWeightInGr = Arrays.asList(1.0, 1000.0); // gr is taken as reference unit	
	
	private static List<HashMap> measurementUnitMetrWeight = buildMeasHashMap(measurementUnitMetrWeightType, measurementUnitMetrWeightInGr);
	
	List<List<String>> allMeasurementUnits = Arrays.asList(measurementUnitUsVolType, measurementUnitUsWeightType, measurementUnitMetrVolType, measurementUnitMetrWeightType);
	
	private double measurementValue;
	private String measurementUnit;
	
	
	// Constructor
	public Measure(double measurementValue, String measurementUnit) throws InvalidParameterException {
		this.measurementValue = measurementValue;
		
		// verify that measurementUnit is valid
		for (List<String> measList : allMeasurementUnits) {
			
			if (measList.contains(measurementUnit)) {
				this.measurementUnit = measurementUnit;
				
				// defining what measurement system is being used, based on measurement unit provided
				if (measurementUnitUsVolType.contains(measurementUnit)) {
					this.setMeasurementType(ie.cct.model.MeasurementSystem.measurementType.volume);
					this.setMeasurmentLocalSystem(ie.cct.model.MeasurementSystem.measurmentLocalSystem.uscs);
				} else if (measurementUnitUsWeightType.contains(measurementUnit)) {
					this.setMeasurementType(ie.cct.model.MeasurementSystem.measurementType.weight);
					this.setMeasurmentLocalSystem(ie.cct.model.MeasurementSystem.measurmentLocalSystem.uscs);			
				} else if (measurementUnitMetrVolType.contains(measurementUnit)) {
					this.setMeasurementType(ie.cct.model.MeasurementSystem.measurementType.volume);
					this.setMeasurmentLocalSystem(ie.cct.model.MeasurementSystem.measurmentLocalSystem.metric);			
				} else if (measurementUnitMetrWeightType.contains(measurementUnit)) {
					this.setMeasurementType(ie.cct.model.MeasurementSystem.measurementType.weight);
					this.setMeasurmentLocalSystem(ie.cct.model.MeasurementSystem.measurmentLocalSystem.metric);			
				}
				
			} else {
				throw new InvalidParameterException();
			}
		
		}
		
	}
	
	// TODO create methods to convert from US to metric
	
	// Helper method to build HashMap of measurement type and multiplier to reference unit
	private static List<HashMap> buildMeasHashMap(List<String> ls, List<Double> ld) {
		
		List<HashMap> measurementUnitUsVol = new ArrayList<>();
		
		for (int i = 0; i < ls.size(); i++) {
			
			HashMap<String, Double> e = new HashMap<String, Double>();	
			e.put(ls.get(i), ld.get(i)); // populate HashMap with name of unit and factor to minimal unit in system
								// teaspoon (tsp) is 1/6 of fluid ounce
			measurementUnitUsVol.add(e);
			
		};
		
		return measurementUnitUsVol;
		
	}
	
	
	public void setMeasurementUnit(String measurementUnit) throws InvalidParameterException {
		
		// 'measurementUnit' is the unit provided by the user
		// check if it matches with any of the available units 
		
		for (List<String> measList : allMeasurementUnits) {
		
			if (measList.contains(measurementUnit)) {
				this.measurementUnit = measurementUnit;
			} else {
				throw new InvalidParameterException();
			}
		
		}
		
	}
	
	
	public void setMeasurementValue (double measurementValue) {
		this.measurementValue = measurementValue;
	}


	// interface method
	@Override
	public void setMeasurmentLocalSystem(ie.cct.model.MeasurementSystem.measurmentLocalSystem measurmentLocalSystem) {
		this.measurmentLocalSystem = measurmentLocalSystem;	
	}

	
	// interface method
	@Override
	public void setMeasurementType(ie.cct.model.MeasurementSystem.measurementType measurementType) {
		this.measurementType = measurementType;
	}


	public MeasurementSystem.measurmentLocalSystem getMeasurmentLocalSystem() {
		return measurmentLocalSystem;
	}


	public MeasurementSystem.measurementType getMeasurementType() {
		return measurementType;
	}


	public double getMeasurementValue() {
		return measurementValue;
	}


	public String getMeasurementUnit() {
		return measurementUnit;
	}

}
