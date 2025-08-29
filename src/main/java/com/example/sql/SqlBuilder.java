package com.example.sql;

public class SqlBuilder {
    public static String getFinalQuery() {
        return "SELECT p.amount AS SALARY, CONCAT(e.first_name, ' ', e.last_name) AS NAME, TIMESTAMPDIFF(YEAR, e.dob, CURDATE()) AS AGE, d.department_name AS DEPARTMENT_NAME FROM payments p JOIN employee e ON e.emp_id = p.emp_id JOIN department d ON d.department_id = e.department WHERE DAY(p.payment_time) <> 1 AND p.amount = (SELECT MAX(p2.amount) FROM payments p2 WHERE DAY(p2.payment_time) <> 1) LIMIT 1";
    }
}
