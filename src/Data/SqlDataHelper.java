package Data;

import Utils.DbConnection;

import java.sql.SQLException;

public class SqlDataHelper {
    private static class SqlHelperSingleton {
        private static final SqlDataHelper INSTANCE = new SqlDataHelper();
    }

    private SqlDataHelper() {}

    public static SqlDataHelper getInstance() {
        return SqlHelperSingleton.INSTANCE;
    }

    public int update(String q) {
        int result = 0;
        try  {
            result = DbConnection.getInstance().connection().createStatement().executeUpdate(q);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
