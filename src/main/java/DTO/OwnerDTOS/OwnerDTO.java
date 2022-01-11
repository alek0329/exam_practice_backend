package DTO.OwnerDTOS;

import entities.Owner;

import java.util.ArrayList;
import java.util.List;

public class OwnerDTO {

    private int ownerId;
    private String name;
    private String address;
    private String phone;

    public OwnerDTO() {
    }

    public OwnerDTO(Owner owner) {
        this.ownerId = owner.getOwnerId();
        this.name = owner.getName();
        this.address = owner.getAddress();
        this.phone = owner.getPhone();
    }

    public static List<OwnerDTO> getDTO(List<Owner> owners) {
        if(owners != null) {
         List <OwnerDTO> ownersDTO = new ArrayList<>();
         owners.forEach(o -> ownersDTO.add(new OwnerDTO(o)));
            return ownersDTO;
        } else {
            return null;
        }
    }

    public int getOwnerId() {
        return ownerId;
    }
    public void setId(int id) {
        this.ownerId = ownerId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        address = address;
    }
}
