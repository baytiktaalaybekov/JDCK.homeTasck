package peaksoft.JDao;

import peaksoft.Config.Util;
import peaksoft.model.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JobDaoImpl implements JobDao {
    Connection connection = Util.getConnection();

    public void createJobTable() {
        String sql = """
                create table if not exists job(
                id serial primary key,
                position varchar,
                profession varchar,
                description varchar,
                experience int)""";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Successfully created job!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void addJob(Job job) {
        String sql = """
                Insert into job(position, profession, description, experience)Values(?,?,?,?)""";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, job.getPosition());
            preparedStatement.setString(2, job.getProfession());
            preparedStatement.setString(3, job.getDescription());
            preparedStatement.setInt(4, job.getExperience());
            preparedStatement.executeUpdate();
            System.out.println("Successfully addJob!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public Job getJobById(Long jobId) {
        Job job = new Job();
        String sql = """
                Select * from job where Id = ?""";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, jobId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                job = new Job(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getInt(5));
                System.out.println("Successfully getJob !");

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return job;
    }

    public List<Job> sortByExperience(String ascOrDesc) {
        List<Job> list = new ArrayList<>();
        switch (ascOrDesc) {
            case "asc":
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement("""
           select * from job order by experience;""");
                   ResultSet resultSet = preparedStatement.executeQuery();
                   while (resultSet.next()){
                       list.add(new Job(resultSet.getLong(1),
                               resultSet.getString(2),
                               resultSet.getString(3),
                               resultSet.getString(4),
                               resultSet.getInt(5)));
                   }resultSet.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "desc":
                try {
                    PreparedStatement preparedStatement = connection .prepareStatement("""
             select * from job order by experience desc;""");
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()){
                        list.add(new Job(resultSet.
                                getLong(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getInt(5)));
                    }resultSet.close();;
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }

                }
        return list;


//        String sql = null;
//        switch (ascOrDesc) {
//            case "asc":
//                sql = """
//                    Select * from job order by experience;
//                    """;
//            break;
//            case "desc":
//                sql = """
//                    Select * from job order by experience desc;
//                    """;
//                break;
//                }
//                try(Statement statement = connection.createStatement()) {
//                    ResultSet resultSet = statement.executeQuery(sql);
//                    while (resultSet.next()){
//                        list.add(new Job(resultSet.getLong(1),
//                                resultSet.getString(2),
//                                resultSet.getString(3),
//                                resultSet.getString(4),
//                                resultSet.getInt(5)));
//                    }
//                    resultSet.close();
//                    System.out.println("Successfully sort!");
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//
//        return list;
    }


    public Job getJobByEmployeeId(Long employeeId) {
        Job job = new Job();
        String sql = """
                Select * from job as job join employee on j.id=employee=.id where job.id = ?""";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1,employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                job = new Job(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getInt(5));
                System.out.println("Successfully getJobByEmp");
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return job;
    }

    public void deleteDescriptionColumn() {
        String sql = """
                alter table job drop column description;""";
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Successfully deleted!");

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
}
