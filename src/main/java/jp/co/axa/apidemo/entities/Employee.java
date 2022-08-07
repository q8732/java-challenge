package jp.co.axa.apidemo.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@EqualsAndHashCode
@Entity
@Table(name="EMPLOYEE")
public class Employee {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name="EMPLOYEE_NAME")
    @NotBlank
    private String name;

    @Getter
    @Setter
    @Column(name="EMPLOYEE_SALARY")
    @Min(0)
    private Integer salary;

    @Getter
    @Setter
    @Column(name="DEPARTMENT")
    @NotBlank
    private String department;

    /**
     * A default constructor is required from ORM
     */
    public Employee() {
        //noop
    }

    public Employee(String name, Integer salary, String department) {
        this.name = name;
        this.salary = salary;
        this.department = department;
    }
}
