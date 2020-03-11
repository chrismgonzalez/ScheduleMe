package Data;

public class TestData {
    public boolean populateAllTables() {
        return populateCountryTable() &&
                populateCityTable() &&
                populateAddressTable() &&
                populateCustomerTable() &&
                populateUserTable() &&
                populateAppointmentsTable();
    }

    public boolean populateCountryTable() {
        String query = "INSERT INTO `country` VALUES \n" +
                "(1,'US','2019-01-01 00:00:00','test','2019-01-01 00:00:00','test'),\n" +
                "(2,'Canada','2019-01-01 00:00:00','test','2019-01-01 00:00:00','test'),\n" +
                "(3,'Norway','2019-01-01 00:00:00','test','2019-01-01 00:00:00','test');";
        int result = SqlDataHelper.getInstance().update(query);
        return result > 0;

    }

    public boolean populateCityTable() {
        String query = "INSERT INTO `city` VALUES \n" +
                "(1,'New York',1,'2019-01-01 00:00:00','test','2019-01-01 00:00:00','test'),\n" +
                "(2,'Los Angeles',1,'2019-01-01 00:00:00','test','2019-01-01 00:00:00','test'),\n" +
                "(3,'Toronto',2,'2019-01-01 00:00:00','test','2019-01-01 00:00:00','test'),\n" +
                "(4,'Vancouver',2,'2019-01-01 00:00:00','test','2019-01-01 00:00:00','test'),\n" +
                "(5,'Oslo',3,'2019-01-01 00:00:00','test','2019-01-01 00:00:00','test');";
        int result = SqlDataHelper.getInstance().update(query);
        return result > 0;
    }

    public boolean populateAddressTable() {
        String query = "INSERT INTO `address` VALUES \n" +
                "(1,'123 Main','',1,'11111','555-1212','2019-01-01 00:00:00','test','2019-01-01 00:00:00','test'),\n" +
                "(2,'123 Elm','',3,'11112','555-1213','2019-01-01 00:00:00','test','2019-01-01 00:00:00','test'),\n" +
                "(3,'123 Oak','',5,'11113','555-1214','2019-01-01 00:00:00','test','2019-01-01 00:00:00','test');";
        int result = SqlDataHelper.getInstance().update(query);
        return result > 0;
    }

    public boolean populateCustomerTable() {
        String query = "INSERT INTO `customer` VALUES \n" +
                "(1,'John Doe',1,1,'2019-01-01 00:00:00','test','2019-01-01 00:00:00','test'),\n" +
                "(2,'Alfred E Newman',2,1,'2019-01-01 00:00:00','test','2019-01-01 00:00:00','test'),\n" +
                "(3,'Ina Prufung',3,1,'2019-01-01 00:00:00','test','2019-01-01 00:00:00','test');";
        int result = SqlDataHelper.getInstance().update(query);
        return result > 0;
    }

    public boolean populateUserTable() {
        String query = "INSERT INTO `user` VALUES \n" +
                "(1,'test','test',1,'2019-01-01 00:00:00','test','2019-01-01 00:00:00','test');";
        int result = SqlDataHelper.getInstance().update(query);
        return result > 0;
    }

    public boolean populateAppointmentsTable() {
        String query = "INSERT INTO `appointment` VALUES \n" +
                "(1,1,1,'not needed','not needed','not needed','not needed','Presentation','not needed','2019-01-01 00:00:00','2019-01-01 00:00:00','2019-01-01 00:00:00','test','2019-01-01 00:00:00','test'),\n" +
                "(2,2,1,'not needed','not needed','not needed','not needed','Scrum','not needed','2019-01-01 00:00:00','2019-01-01 00:00:00','2019-01-01 00:00:00','test','2019-01-01 00:00:00','test');";
        int result = SqlDataHelper.getInstance().update(query);
        return result > 0;
    }
}
