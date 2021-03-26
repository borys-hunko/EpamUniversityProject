package com.epam.EpamUniversityProject.repository.dao.interfaces;

import com.epam.EpamUniversityProject.model.Application;

import java.sql.SQLException;
import java.util.List;

public interface ApplicationDao extends Dao<Application>{
    List<Application> getUsersApplication(long userID) throws SQLException;
}
