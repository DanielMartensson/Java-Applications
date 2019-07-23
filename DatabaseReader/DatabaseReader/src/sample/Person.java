package sample;

import javafx.beans.property.SimpleStringProperty;

public class Person {
    private SimpleStringProperty employee, wage, company, age, on_vacation, experience;

    public Person(String employee, String wage, String company, String age, String on_vacation, String experience) {
        this.employee = new SimpleStringProperty(employee);
        this.wage = new SimpleStringProperty(wage);
        this.company = new SimpleStringProperty(company);
        this.age = new SimpleStringProperty(age);
        this.on_vacation = new SimpleStringProperty(on_vacation);
        this.experience = new SimpleStringProperty(experience);
    }

    public String getEmployee() {
        return employee.get();
    }

    public SimpleStringProperty employeeProperty() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee.set(employee);
    }

    public String getWage() {
        return wage.get();
    }

    public SimpleStringProperty wageProperty() {
        return wage;
    }

    public void setWage(String wage) {
        this.wage.set(wage);
    }

    public String getCompany() {
        return company.get();
    }

    public SimpleStringProperty companyProperty() {
        return company;
    }

    public void setCompany(String company) {
        this.company.set(company);
    }

    public String getAge() {
        return age.get();
    }

    public SimpleStringProperty ageProperty() {
        return age;
    }

    public void setAge(String age) {
        this.age.set(age);
    }

    public String getOn_vacation() {
        return on_vacation.get();
    }

    public SimpleStringProperty on_vacationProperty() {
        return on_vacation;
    }

    public void setOn_vacation(String on_vacation) {
        this.on_vacation.set(on_vacation);
    }

    public String getExperience() {
        return experience.get();
    }

    public SimpleStringProperty experienceProperty() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience.set(experience);
    }
}
