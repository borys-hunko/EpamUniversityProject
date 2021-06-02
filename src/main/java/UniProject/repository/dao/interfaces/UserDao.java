package UniProject.repository.dao.interfaces;

import UniProject.model.User;

import java.io.IOException;
import java.sql.SQLException;

public interface UserDao extends Dao<User> {
    User getByEmail(String email) throws IOException, SQLException;
}
