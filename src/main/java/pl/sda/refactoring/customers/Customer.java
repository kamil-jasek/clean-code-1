package pl.sda.refactoring.customers;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * The customer, can be person or company
 */
public class Customer {

    // customer types
    public static final int COMPANY = 1;
    public static final int PERSON = 2;

    private UUID id;
    private int type;
    private LocalDateTime ctime;

    private Email email;
    private LocalDateTime verfTime;
    private boolean verf;
    private CustomerVerifier verifBy;

    // company data
    private Name compName;
    private Vat compVat;

    // person data
    private Name fName;
    private Name lName;
    private Pesel pesel;

    // address data
    private String addrStreet;
    private String addrCity;
    private String addrZipCode;
    private String addrCountryCode;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public LocalDateTime getCtime() {
        return ctime;
    }

    public void setCtime(LocalDateTime ctime) {
        this.ctime = ctime;
    }

    public Name getCompName() {
        return compName;
    }

    public void setCompName(Name compName) {
        this.compName = compName;
    }

    public Vat getCompVat() {
        return compVat;
    }

    public void setCompVat(Vat compVat) {
        this.compVat = compVat;
    }

    public Name getfName() {
        return fName;
    }

    public void setfName(Name fName) {
        this.fName = fName;
    }

    public Name getlName() {
        return lName;
    }

    public void setlName(Name lName) {
        this.lName = lName;
    }

    public Pesel getPesel() {
        return pesel;
    }

    public void setPesel(Pesel pesel) {
        this.pesel = pesel;
    }

    public String getAddrStreet() {
        return addrStreet;
    }

    public void setAddrStreet(String addrStreet) {
        this.addrStreet = addrStreet;
    }

    public String getAddrCity() {
        return addrCity;
    }

    public void setAddrCity(String addrCity) {
        this.addrCity = addrCity;
    }

    public String getAddrZipCode() {
        return addrZipCode;
    }

    public void setAddrZipCode(String addrZipCode) {
        this.addrZipCode = addrZipCode;
    }

    public String getAddrCountryCode() {
        return addrCountryCode;
    }

    public void setAddrCountryCode(String addrCountryCode) {
        this.addrCountryCode = addrCountryCode;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public LocalDateTime getVerfTime() {
        return verfTime;
    }

    public void setVerfTime(LocalDateTime verfTime) {
        this.verfTime = verfTime;
    }

    public boolean isVerf() {
        return verf;
    }

    public void setVerf(boolean verf) {
        this.verf = verf;
    }

    public CustomerVerifier getVerifBy() {
        return verifBy;
    }

    public void setVerifBy(CustomerVerifier verifBy) {
        this.verifBy = verifBy;
    }

    void markVerified() {
        setVerf(true);
        setVerfTime(LocalDateTime.now());
        setVerifBy(CustomerVerifier.AUTO_EMAIL);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customer customer = (Customer) o;
        return type == customer.type && verf == customer.verf && Objects.equals(id, customer.id)
            && Objects.equals(ctime, customer.ctime) && Objects.equals(email, customer.email)
            && Objects.equals(verfTime, customer.verfTime) && verifBy == customer.verifBy && Objects
            .equals(compName, customer.compName) && Objects.equals(compVat, customer.compVat) && Objects
            .equals(fName, customer.fName) && Objects.equals(lName, customer.lName) && Objects
            .equals(pesel, customer.pesel) && Objects.equals(addrStreet, customer.addrStreet) && Objects
            .equals(addrCity, customer.addrCity) && Objects.equals(addrZipCode, customer.addrZipCode)
            && Objects.equals(addrCountryCode, customer.addrCountryCode);
    }

    @Override
    public int hashCode() {
        return Objects
            .hash(id, type, ctime, email, verfTime, verf, verifBy, compName, compVat, fName, lName, pesel, addrStreet,
                addrCity, addrZipCode, addrCountryCode);
    }
}
