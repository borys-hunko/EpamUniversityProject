package com.epam.EpamUniversityProject.repository.dao.interfaces;

import com.epam.EpamUniversityProject.model.Application;
import com.epam.EpamUniversityProject.model.Grade;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface GradeDao extends Dao<Grade>{
    List<Grade> getApplicationGrades(long id) throws SQLException;
}

