package org.ABCCompany;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class PrintCompanyData {

    private static Integer ceoId;

    public static void main(String[] args) {
        Map<Integer, Employee> empMap = loadCSV();
        PrintCompanyData printCompanyData = new PrintCompanyData();
        printCompanyData.findManagerWithLessSalary(empMap);
        printCompanyData.findHierarchy(empMap);
        System.out.println("Hello World!");
    }


    public static Map<Integer, Employee> loadCSV() {
        Map<Integer, Employee> hm = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("/Users/shashanktiwari/Downloads/OrgStructAnalysis/src/main/java/org/ABCCompany/resources/Employee.csv"))) {
            String line ;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] strArr = line.split(",");
                Integer id = Integer.valueOf(strArr[0]);
                Integer salary = Integer.valueOf(strArr[3]);
                Integer managerId = null;
                if (5 == strArr.length && null != strArr[4]) {
                    managerId = Integer.valueOf(strArr[4]);
                } else {
                    ceoId = id;
                }
                Employee emp = new Employee(id, strArr[1], strArr[2], salary, managerId);
                hm.put(id, emp);
            }
        } catch (IOException ex) {
            System.out.println(" exception occurred " + ex);
        }
        return hm;
    }

    public void findManagerWithLessSalary(Map<Integer, Employee> empMap) {
        Map<Integer, List<Employee>> mngrEmpMap = new HashMap<>();
        for (Map.Entry<Integer, Employee> entry : empMap.entrySet()) {
            Integer empId = entry.getKey();
            Employee emp = entry.getValue();
            if (!empId.equals(ceoId)) {
                List<Employee> directReportList = new ArrayList<>();
                if (mngrEmpMap.containsKey(emp.getManagerId())) {
                    directReportList = mngrEmpMap.get(emp.getManagerId());
                }
                directReportList.add(emp);
                mngrEmpMap.put(emp.getManagerId(), directReportList);
                //System.out.println("mngrEmpMap----> after if block "+mngrEmpMap);
            }
        }
        Map<Double, Employee> mngWithLessSal = new LinkedHashMap<>();
        Map<Double, Employee> mngWithMoreSal = new LinkedHashMap<>();
        for (Map.Entry<Integer, List<Employee>> mgMap : mngrEmpMap.entrySet()) {
            double avgSal = mgMap.getValue().stream().map(Employee::getSalary).mapToDouble(Integer::doubleValue).average().getAsDouble();
            Integer mngId = mgMap.getKey();
            Double mngSal = Double.valueOf(empMap.get(mngId).getSalary());
            Double diffPer = (((mngSal - avgSal) / avgSal) * 100);
            final DecimalFormat df = new DecimalFormat("0.00");
            df.setRoundingMode(RoundingMode.UP);

            double diff = Double.valueOf(df.format(diffPer));
            if (diff <= 20) {
                mngWithLessSal.put(diff, empMap.get(mngId));
            }
            if (diff > 50) {
                mngWithMoreSal.put(diff, empMap.get(mngId));
            }
            System.out.println("per ----> " + diffPer + " Employee Id: " + mngId + " average sal :" + avgSal + "  mngSal:  " + mngSal);

        }
        System.out.println("Below are the Employee ID and name of manager's who earns less than 20% of average salary of their subordinates and by how much respectively");
        mngWithLessSal.entrySet().forEach(hm -> System.out.println("Employee Id: " + hm.getValue().getId() + " and Name: " + hm.getValue().getFirstName() + " " + hm.getValue().getLastName() + " also the difference in salary is by " + hm.getKey() + "%"));
        System.out.println(" *********** ");
        System.out.println("Below are the Employee ID and name of manager's who earns more than 50% of average salary of their subordinates and by how much respectively");
        mngWithMoreSal.entrySet().forEach(hm -> System.out.println("Employee Id: " + hm.getValue().getId() + " and Name: " + hm.getValue().getFirstName() + " " + hm.getValue().getLastName() + " also the difference in salary is by " + hm.getKey() + "%"));

    }

    public void findHierarchy(Map<Integer, Employee> empMap) {
        System.out.println("--------------------------------------------------------");
        int hierarchyVal = 0;
        for (Map.Entry<Integer, Employee> hm : empMap.entrySet()) {
            int val = returnHierarchy(hierarchyVal, hm.getValue(), empMap) - 1;
            if (val > 4) {
                System.out.println(" Below is the employee having long hierarchy : ");
                System.out.println(" Hierarchy : " + val + " empId " + hm.getValue().getId() + " Employee Name :" + hm.getValue().getFirstName() + hm.getValue().getLastName());
            }
        }

    }

    private Integer returnHierarchy(int hierarchyVal, Employee employee, Map<Integer, Employee> empMap) {
        if (employee.getManagerId() == null) {
            return hierarchyVal;
        }
        Employee emp = empMap.get(employee.getManagerId());
        hierarchyVal = hierarchyVal + 1;
        return returnHierarchy(hierarchyVal, emp, empMap);
    }


}
