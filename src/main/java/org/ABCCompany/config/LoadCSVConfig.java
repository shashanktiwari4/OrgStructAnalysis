package org.ABCCompany.config;

import org.ABCCompany.Main;
import org.ABCCompany.domain.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class LoadCSVConfig {

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
                    Main.ceoId = id;
                }
                Employee emp = new Employee(id, strArr[1], strArr[2], salary, managerId);
                hm.put(id, emp);
            }
        } catch (IOException ex) {
            System.out.println(" exception occurred " + ex);
        }
        return hm;
    }



}
