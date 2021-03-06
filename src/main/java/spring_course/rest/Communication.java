package spring_course.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import spring_course.rest.entity.Employee;

import java.util.List;

@Component
public class Communication {
    private final String URL = "http://localhost:8080/spring_rest_json_app/api/employees";

    @Autowired
    private RestTemplate restTemplate;

    public List<Employee> getAllEmployees() {

        ResponseEntity<List<Employee>> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {});

        List<Employee> allEmployees = responseEntity.getBody();
        return allEmployees;
    }

//    public ResponseEntity<Employee> getEmployee(int id){
//        ResponseEntity<Employee> employee = restTemplate.exchange(URL + "/" + id, HttpMethod.GET, null, new ParameterizedTypeReference<Employee>() {});
//        return employee;
//    }
    public Employee getEmployeeById(int id) {
        Employee employee = restTemplate.getForObject(URL + "/" + id, Employee.class);
        return employee;
    }

    public void saveEmployee(Employee employee) {
        int id = employee.getId();
        if (id == 0) {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL, employee, String.class);
            System.out.println("New employee was added to DB");
            System.out.println(responseEntity.getBody());
        } else {
            restTemplate.put(URL, employee);
            System.out.println("Employee with ID=" + id + " was successfully updated");
        }
    }
    public void deleteEmployee(int id) {
        restTemplate.delete(URL + "/" + id);
        System.out.println("The Employee with ID = " + id + " was successfully deleted");
    }
}
