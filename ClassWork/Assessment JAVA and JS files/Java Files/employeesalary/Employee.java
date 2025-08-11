package com.yogesh.employeesalary;

class Employee {
    private int empNo;
    private String name;
    private String department;
    private int salary;

    public Employee(int empNo, String name, String department, int salary) {
        this.empNo = empNo;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public int getEmpNo() {
        return empNo;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public int getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empNo=" + empNo +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", salary=" + salary +
                '}';
    }
}