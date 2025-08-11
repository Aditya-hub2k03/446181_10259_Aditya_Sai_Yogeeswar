package com.yogesh.employeesalary;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();

        employees.add(new Employee(1, "Aditya", "Engineering", 75000));
        employees.add(new Employee(2, "Sai", "Marketing", 80000));
        employees.add(new Employee(3, "Yogeeswar", "Sales", 85000));
        employees.add(new Employee(4, "Pyda", "HR", 70000));
        employees.add(new Employee(5, "ASYP", "Finance", 90000));

        for (Employee emp : employees) {
            System.out.println(emp);
        }

        Employee highestPaidEmployee = employees.get(0);
        for (Employee emp : employees) {
            if (emp.getSalary() > highestPaidEmployee.getSalary()) {
                highestPaidEmployee = emp;
            }
        }

        System.out.println("Employee with the highest salary: " + highestPaidEmployee);
    }
}