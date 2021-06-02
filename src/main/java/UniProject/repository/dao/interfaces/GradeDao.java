package UniProject.repository.dao.interfaces;

import UniProject.model.Grade;

import java.sql.SQLException;
import java.util.List;

public interface GradeDao extends Dao<Grade>{
    List<Grade> getApplicationGrades(long id) throws SQLException;
}

