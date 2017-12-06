package br.com.caelum.fj91.bytecode.models.vo;

import javax.persistence.Embeddable;
import java.util.Optional;

@Embeddable
public class Address {

    private String street;
    private Long number;
    private Optional<Long> apartment;
    private String zipCode;

    /**
     * @deprecated frameworks only
     */
    public Address() {
    }

    public Address(String street, Long number, Optional<Long> apartment, String zipCode) {
        this.street = street;
        this.number = number;
        this.apartment = apartment;
        this.zipCode = zipCode;
    }

    public String getZipCode() {
        return zipCode;
    }


    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", number=" + number +
                ", apartment=" + apartment +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }

    public static class Buider{

        private Buider(){}

        public static SteetPart number(Number number){
            return new SteetPart(number.longValue());
        }
    }

    public static class SteetPart {
        private final Long number;

        private SteetPart(Long number) {
            this.number = number;
        }

        public ApartmentPart ofStreet(String street){
            return new ApartmentPart(number, street);
        }
    }


    public static class ApartmentPart {
        private final Long number;
        private final String street;

        private ApartmentPart(Long number, String street) {
            this.number = number;
            this.street = street;
        }


        public ZipCodePart andApartment(Long apartment){
            return new ZipCodePart(number, street, apartment);
        }

        public BuilderAddress zipCode(String zipCode){
            return new BuilderAddress(number, street, Optional.empty(), zipCode);
        }
    }

    public static class ZipCodePart {


        private final Long number;
        private final String street;
        private final Long apartment;

        private ZipCodePart(Long number, String street, Long apartment) {

            this.number = number;
            this.street = street;
            this.apartment = apartment;
        }

        public BuilderAddress zipCode(String zipCode){
            return new BuilderAddress(number, street, Optional.of(apartment), zipCode);
        }

    }
    public static class BuilderAddress {
        private final Long number;
        private final String street;
        private final Optional<Long> apartment;
        private final String zipCode;

        private BuilderAddress(Long number, String street, Optional<Long> apartment, String zipCode) {
            this.number = number;
            this.street = street;
            this.apartment = apartment;
            this.zipCode = zipCode;
        }

        public Address build(){
            return new Address(street,number,apartment, zipCode);
        }
    }
}
