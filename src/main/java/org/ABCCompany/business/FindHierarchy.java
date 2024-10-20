package org.ABCCompany.business;

import org.ABCCompany.domain.Employee;

import java.util.Map;

public class FindHierarchy {

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
