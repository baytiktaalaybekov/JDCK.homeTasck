package peaksoft;

import peaksoft.Config.Util;
import peaksoft.Dao.EmployeeDao;
import peaksoft.EmplService.EmployeeService;
import peaksoft.EmplService.EmployeeServiceImpl;
import peaksoft.JDao.JobDao;
import peaksoft.JobServices.JobService;
import peaksoft.JobServices.JobServiceImpl;
import peaksoft.model.Employee;
import peaksoft.model.Job;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
//        Util.getConnection();
        Scanner scanner =new Scanner(System.in);
        JobService jobService = new JobServiceImpl();
        EmployeeService employeeService = new EmployeeServiceImpl();

        while (true){
            System.out.println("""
                    <<< Command Job >>>
                    1.Press -> createJobTable.
                    2.Press -> addJob.
                    3.Press -> getJobById.
                    4.Press -> sortByExperience.
                    5.Press -> getJobByEmployeeId.
                    6.Press -> deleteDescriptionColumn.
                    =========================================
                    <<< Command Employee >>>
                    7.Press -> createEmployee.
                    8.Press -> addEmployee.
                    9.Press -> dropTable.
                    10.Press -> cleanTable.
                    11.Press -> updateEmployee.
                    12.Press -> getAllEmployee.
                    13.Press ->  findByEmail.
                    14.Press -> getEmployeeById.
                    15.Press -> getEmployeeByPosition.
                    
                    Enter by command:
                    """);
            int choose = scanner.nextInt();
            switch (choose){
                case 1:
                    jobService.createJobTable();
                    break;
                case 2:
                    System.out.println("Select position: (Mentor, Management, Instructor)");
                    String position = new Scanner(System.in).nextLine();
                    System.out.println("Select profession: (Java, Js)");
                    String profession = new Scanner(System.in).nextLine();
                    System.out.println("Select description: (Backend, Fronted)");
                    String description = new Scanner(System.in).nextLine();
                    System.out.println("Enter experience: ");
                    int experience = new Scanner(System.in).nextInt();
                    Job job = new Job(position,profession,description, experience);
                    jobService.addJob(job);
                    break;
                case 3:
                    jobService.getJobById(1L);

                    break;
                case 4:
                    System.out.println("1(asc),2(desc): ");
                    System.out.println(jobService.sortByExperience(new Scanner(System.in).nextLine()));
                    break;
                case 5:
                    jobService.getJobByEmployeeId(1L);
                    break;
                case 6:
                    jobService.deleteDescriptionColumn();
                    break;
                case 7:
                    employeeService.createEmployee();
                    break;
                case 8:
                    System.out.println("Write first name :");
                    String firstName = new Scanner(System.in).nextLine();
                    System.out.println("Write last name :");
                    String lastName = new Scanner(System.in).nextLine();
                    System.out.println("Write age :");
                    int age = new Scanner(System.in).nextInt();
                    System.out.println("Write email:");
                    String email = new Scanner(System.in).nextLine();
                    System.out.println("Write job id :");
                    int jobId = new Scanner(System.in).nextInt();
                    employeeService.addEmployee(new Employee(firstName,lastName,age,email,jobId));
                    break;
                case 9:
                    employeeService.dropTable();
                    break;
                case 10:
                    employeeService.cleanTable();
                    break;
                case 11:
                    System.out.println("Write id ");
                    long id = new Scanner(System.in).nextLong();
                    System.out.println("Write first name :");
                    String firstName1 = new Scanner(System.in).nextLine();
                    System.out.println("Write last name :");
                    String lastName1 = new Scanner(System.in).nextLine();
                    System.out.println("Write age :");
                    int age1 = new Scanner(System.in).nextInt();
                    System.out.println("Write email:");
                    String email1 = new Scanner(System.in).nextLine();
                    System.out.println("Write job id :");
                    int jobId1 = new Scanner(System.in).nextInt();
                    employeeService.updateEmployee(id,new Employee(firstName1,lastName1,age1,email1,jobId1));
                    break;
                case 12:
                    System.out.println(employeeService.getAllEmployees());
                    break;
                case 14:
                    System.out.println("Write email: ");
                    System.out.println(employeeService.findByEmail(new Scanner(System.in).nextLine()));
                    break;
                case 15:
                    System.out.println("Write employee id: ");
                    System.out.println(employeeService.getEmployeeById(new Scanner(System.in).nextLong()));
                    break;
                case 16:
                    System.out.println("Write position: ");
                    System.out.println(employeeService.getEmployeeByPosition(new Scanner(System.in).nextLine()));
                    break;


            }
        }








    }
}
