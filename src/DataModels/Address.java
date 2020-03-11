package DataModels;

import java.util.Objects;

public class Address {
    private int addressId;
    private String address;
    private String address2;
    private int cityId;
    private String postalCode;
    private String phone;

    public Address(int addressId, String address, String address2, int cityId, String postalCode, String phone) {
        this.addressId = addressId;
        this.address = address;
        this.address2 = address2;
        this.cityId = cityId;
        this.postalCode = postalCode;
        this.phone = phone;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address1 = (Address) o;
        return getCityId() == address1.getCityId() &&
                Objects.equals(getAddress(), address1.getAddress()) &&
                Objects.equals(getAddress2(), address1.getAddress2()) &&
                Objects.equals(getPostalCode(), address1.getPostalCode()) &&
                Objects.equals(getPhone(), address1.getPhone());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAddress(), getAddress2(), getCityId(), getPostalCode(), getPhone());
    }
}
