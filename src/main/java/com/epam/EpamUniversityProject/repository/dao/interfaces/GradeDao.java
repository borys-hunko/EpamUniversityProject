package com.epam.EpamUniversityProject.repository.dao.interfaces;

import com.epam.EpamUniversityProject.model.Grade;

import java.util.List;

public interface GradeDao extends Dao{
    List<Grade> getUsersGrades(long id);
}
