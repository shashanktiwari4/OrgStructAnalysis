package org.ABCCompany;

import org.ABCCompany.business.FindHierarchy;
import org.ABCCompany.business.FindManagerData;
import org.ABCCompany.config.LoadCSVConfig;
import org.ABCCompany.domain.Employee;

import java.util.*;

public class Main {

    public static Integer ceoId;

    public static void main(String[] args) {
        Map<Integer, Employee> empMap = LoadCSVConfig.loadCSV();
        FindManagerData findManagerData =new FindManagerData();
        findManagerData.findManagerWithLessAndMoreSalary(empMap);
        FindHierarchy findHierarchy =new FindHierarchy();
        findHierarchy.findHierarchy(empMap);
    }


}
