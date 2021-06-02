package UniProject.repository.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {
    void add(T item) throws SQLException;
    T get(long id) throws SQLException;
    void update(T newItem) throws SQLException;
    void delete(long id) throws SQLException;
    List<T> getAll() throws SQLException;
}
