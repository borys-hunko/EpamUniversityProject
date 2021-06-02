package UniProject.repository.dao.interfaces;

import UniProject.model.Application;

import java.sql.SQLException;
import java.util.List;

public interface ApplicationDao extends Dao<Application>{
    List<Application> getUsersApplication(long userID) throws SQLException;
    void updateStatusForAllApplications(List<Application> applications) throws SQLException;
}
