package Test;

public class TestDataGen {
    public boolean populateAllTables() {
        return populateCountryTable() &&
                populateCityTable() &&
                populateAddressTable() &&
                populateCustomerTable() &&
                populateUserTable() &&
                populateAppointmentsTable;
    }


}
