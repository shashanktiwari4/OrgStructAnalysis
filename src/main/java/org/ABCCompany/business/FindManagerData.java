package org.ABCCompany.business;

import org.ABCCompany.Main;
import org.ABCCompany.domain.Employee;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class FindManagerData {

    public void findManagerWithLessAndMoreSalary(Map<Integer, Employee> empMap) {
        Map<Integer, List<Employee>> mngrEmpMap = new HashMap<>();
        populateManagerMap(empMap, mngrEmpMap);
        Map<Double, Employee> mngWithLessSal = new LinkedHashMap<>();
        Map<Double, Employee> mngWithMoreSal = new LinkedHashMap<>();
        for (Map.Entry<Integer, List<Employee>> mgMap : mngrEmpMap.entrySet()) {
            populateManagerMapWithLessAndMoreAvgSal(empMap, mngWithLessSal, mngWithMoreSal, mgMap);
            //System.out.println("per ----> " + diffPer + " Employee Id: " + mngId + " average sal :" + avgSal + "  mngSal:  " + mngSal);
        }
        printOutput(mngWithLessSal, mngWithMoreSal);

    }

    private void populateManagerMapWithLessAndMoreAvgSal(Map<Integer, Employee> empMap, Map<Double, Employee> mngWithLessSal, Map<Double, Employee> mngWithMoreSal, Map.Entry<Integer, List<Employee>> mgMap) {
        double avgSal = mgMap.getValue().stream().map(Employee::getSalary).mapToDouble(Integer::doubleValue).average().getAsDouble();
        Integer mngId = mgMap.getKey();
        Double mngSal = Double.valueOf(empMap.get(mngId).getSalary());
        Double diffPer = (((mngSal - avgSal) / avgSal) * 100);
        final DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.UP);

        double diff = Double.valueOf(df.format(diffPer));
        if (diff < 20) {
            mngWithLessSal.put(diff, empMap.get(mngId));
        }
        if (diff > 50) {
            mngWithMoreSal.put(diff, empMap.get(mngId));
        }
    }

    private void populateManagerMap(Map<Integer, Employee> empMap, Map<Integer, List<Employee>> mngrEmpMap) {
        for (Map.Entry<Integer, Employee> entry : empMap.entrySet()) {
            Integer empId = entry.getKey();
            Employee emp = entry.getValue();
            if (!empId.equals(Main.ceoId)) {
                List<Employee> directReportList = new ArrayList<>();
                if (mngrEmpMap.containsKey(emp.getManagerId())) {
                    directReportList = mngrEmpMap.get(emp.getManagerId());
                }
                directReportList.add(emp);
                mngrEmpMap.put(emp.getManagerId(), directReportList);
                //System.out.println("mngrEmpMap----> after if block "+mngrEmpMap);
            }
        }
    }

    private void printOutput(Map<Double, Employee> mngWithLessSal, Map<Double, Employee> mngWithMoreSal) {
        System.out.println("Below are the Employee ID and name of manager's who earns less than 20% of average salary of their subordinates and by how much respectively");
        mngWithLessSal.entrySet().forEach(hm -> System.out.println("Employee Id: " + hm.getValue().getId() + " and Name: " + hm.getValue().getFirstName() + " " + hm.getValue().getLastName() + " also the difference in salary is by " + hm.getKey() + "%"));
        System.out.println(" *********** ");
        System.out.println("Below are the Employee ID and name of manager's who earns more than 50% of average salary of their subordinates and by how much respectively");
        mngWithMoreSal.entrySet().forEach(hm -> System.out.println("Employee Id: " + hm.getValue().getId() + " and Name: " + hm.getValue().getFirstName() + " " + hm.getValue().getLastName() + " also the difference in salary is by " + hm.getKey() + "%"));
    }
}
