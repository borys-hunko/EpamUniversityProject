package com.epam.EpamUniversityProject.repository.dao.impl;

import com.epam.EpamUniversityProject.model.Faculty;
import com.epam.EpamUniversityProject.model.Subject;
import com.epam.EpamUniversityProject.repository.dao.interfaces.FacultyDao;
import com.epam.EpamUniversityProject.repository.util.DBManager;
import com.epam.EpamUniversityProject.repository.util.Fields;
import com.epam.EpamUniversityProject.repository.util.Mapper;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacultyDaoPostgresImpl implements FacultyDao {
    private final Logger log = Logger.getLogger(FacultyDaoPostgresImpl.class);
    private final Mapper<Faculty> facultyMapper = new FacultyMapper();
    private final Mapper<Subject> subjectMapper = new SubjectDaoPostgresImpl.SubjectMapper();
    private final DBManager manager = DBManager.getInstance();

    private static final String SQL_GET_ALL_FACULTIES = "select * from faculty;";
    private static final String SQL_GET_ALL_REQUIRES_SUBJECTS = "select s.id,s.name " +
            "from subject s join required_subjects rs on s.id = rs.subject" +
            " join faculty f on f.id = rs.faculty where f.id=?;";

    @Override
    public void add(Faculty item) throws SQLException {

    }

    @Override
    public Faculty get(long id) throws SQLException {
        return null;
    }

    @Override
    public void update(Faculty newItem) throws SQLException {

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public List<Faculty> getAll() throws SQLException {
        Connection connection = null;
        Statement facsStatement = null;
        PreparedStatement subjsStatement = null;
        ResultSet facsRs = null;
        List<Faculty> faculties = new ArrayList<>();
        try {
            connection = manager.getConnection();
            facsStatement = connection.createStatement();
            subjsStatement = connection.prepareStatement(SQL_GET_ALL_REQUIRES_SUBJECTS);
            facsRs = facsStatement.executeQuery(SQL_GET_ALL_FACULTIES);
            while (facsRs.next()) {
                Faculty faculty = facultyMapper.mapRow(facsRs);
                subjsStatement.setLong(1, faculty.getId());
                List<Subject> subjects = getRequiredSubjects(subjsStatement);
                faculty.setRequiredSubjects(subjects);
                faculties.add(faculty);
            }
            return faculties;
        } catch (SQLException e) {
            log.error("getAll->" + e.getMessage());
            throw e;
        } finally {
            manager.close(facsRs)
                    .close(facsStatement)
                    .close(subjsStatement)
                    .close(connection);
        }
    }

    private List<Subject> getRequiredSubjects(PreparedStatement subjsStatement) throws SQLException {
        ResultSet subjsRs;
        subjsRs = subjsStatement.executeQuery();
        List<Subject> subjects = new ArrayList<>();
        while (subjsRs.next()) {
            subjects.add(subjectMapper.mapRow(subjsRs));
        }
        return subjects;
    }

    private class FacultyMapper implements Mapper<Faculty> {

        @Override
        public Faculty mapRow(ResultSet resultSet) {
            Faculty faculty = new Faculty();
            try {
                faculty.setId(resultSet.getLong(Fields.FACULTY_ID));
                faculty.setName(resultSet.getString(Fields.FACULTY_NAME));
                faculty.setBudgedPlaces(resultSet.getInt(Fields.FACULTY_BUDGET_PLACES));
                faculty.setTotalPlaces(resultSet.getInt(Fields.FACULTY_TOTAL_PLACES));
                return faculty;
            } catch (SQLException e) {
                log.error("mapRow->" + e.getMessage());
                return null;
            }
        }
    }

    public static void main(String[] args) {
        try {
            FacultyDao dao = new FacultyDaoPostgresImpl();
            List<Faculty> faculties = dao.getAll();
            for (Faculty faculty : faculties) {
                System.out.println(faculty);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
