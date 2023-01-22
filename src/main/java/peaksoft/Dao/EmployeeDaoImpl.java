package peaksoft.Dao;

import peaksoft.Config.Util;
import peaksoft.model.Employee;
import peaksoft.model.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EmployeeDaoImpl implements  EmployeeDao{

    Connection connection = Util.getConnection();

    public void createEmployee() {
        String sql = """
                create table if not exists employee(
                id serial primary key,
                first_name varchar,
                last_name varchar,
                age smallint not null,
                email varchar,
                job_id int references job(id));
                """;
        try (Connection connection = Util.getConnection();){
            assert connection != null;
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            System.out.println("Successfully created!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void addEmployee(Employee employee) {
        String sql = """ 
 insert into employee(first_name, last_name, age , email, job_id)Values(?,?,?,?,?)""";
        try(Connection connection = Util.getConnection()){
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setByte(3, (byte) employee.getAge());
            preparedStatement.setString(4, employee.getEmail());
            preparedStatement.setLong(5,employee.getJobId());
            System.out.println(preparedStatement.executeUpdate());
            System.out.println("Successfully addEmployee!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropTable() {
        String sql = """
                Drop table employee;
                """;
        try(Connection connection = Util.getConnection()){
            assert connection != null;
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            System.out.println("Successfully drop!");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }


    }

    public void cleanTable() {
        String sql = """
                truncate table user;
                """;
        try(Connection connection = Util.getConnection()){
            assert connection != null;
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            System.out.println("Successfully cleaned!");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    public void updateEmployee(Long id, Employee employee) {
        String sql = """
                Update employee first_name = ?,
                last_name= ?,
                age = ?,
                email= ?,
                job_id =? where id =?""";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1,employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setInt(3,employee.getAge());
            preparedStatement.setString(4, employee.getEmail());
            preparedStatement.setInt(5,employee.getJobId());
            preparedStatement.executeUpdate();
            System.out.println("Successfully updated!!!");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> list = new ArrayList<>();
        Employee employee =new Employee();
        String sql = """
                Select * from Employee;
                """;
        try(Connection connection = Util.getConnection()) {
            assert connection != null;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                list.add(employee= new Employee());
                employee.setId(resultSet.getLong("id"));
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setAge(resultSet.getInt("age"));
                employee.setEmail(resultSet.getString("email"));
                employee.setJobId(resultSet.getInt("job_id"));

            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public Employee findByEmail(String email) {
        Employee employee = new Employee();
        String sql = """
                Select * from employee where email=?""";

        try(Connection connection= Util.getConnection()) {
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                employee = new Employee(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getByte(4),
                        resultSet.getString(5),
                        resultSet.getInt(6));
                System.out.println("Successfully find!");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return employee;

    }

    @Override
    public Map<Employee, Job> getEmployeeById(Long employeeId) {
        Map<Employee,Job> jobMap = new LinkedHashMap<>();
        String sql = """
                Select * from employee join job on employee.id=job.id where employee.id =?; """;
        try (PreparedStatement preparedStatement=connection.prepareStatement(sql)){
            preparedStatement.setLong(1,employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Employee employee= new Employee();
                employee.setId(resultSet.getLong("id"));
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setAge(resultSet.getInt("age"));
                employee.setEmail(resultSet.getString("email"));
                employee.setJobId(resultSet.getInt("job_id"));
                Job job = new Job();
                job.setId(resultSet.getLong("id"));
                job.setPosition(resultSet.getString("position"));
                job.setProfession(resultSet.getString("profession"));
                job.setDescription(resultSet.getString("description"));
                job.setExperience(resultSet.getInt("experince"));
                jobMap.put(employee,job);
            }resultSet.close();

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return jobMap;

            }




    @Override
    public List<Employee> getEmployeeByPosition(String position) {
        List<Employee> list =new ArrayList<>();
        String sql = """
                Select * from employee where position = ?""";
        try {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                list.add(new Employee(resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        (byte) resultSet.getInt("age"),
                        resultSet.getString("email"),
                        resultSet.getInt("job_id")));
                System.out.println("Successfully getEmployeeByP");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
}




