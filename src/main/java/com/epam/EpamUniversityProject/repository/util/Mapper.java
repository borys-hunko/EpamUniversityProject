package com.epam.EpamUniversityProject.repository.util;

import java.sql.ResultSet;

public interface Mapper <T>{
    T mapRow(ResultSet resultSet);
}
