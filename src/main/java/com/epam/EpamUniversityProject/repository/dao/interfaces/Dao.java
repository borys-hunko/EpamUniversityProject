package com.epam.EpamUniversityProject.repository.dao.interfaces;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {
    void add(T item) throws SQLException;
    T get(long id) throws SQLException;
    void update(T newItem) throws SQLException;
    void delete(long id);
    List<T> getAll() throws SQLException;
}
