package br.com.caelum.fj91.bytecode.models;

import br.com.caelum.fj91.bytecode.models.vo.Address;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Studenty {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Address address;

    /**
     * @deprecated frameworks only
     */
    private Studenty() {
    }

    public Studenty(Long id, String name, Address address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public String getZipCode(){
        getAddress();
        getAddress();
        return getAddress().getZipCode();
    }

    public Address getAddress() {
        throw new RuntimeException();
    }


    @Override
    public String toString() {
        return "Studenty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                '}';
    }
}
