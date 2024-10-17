package com.spring.jwt.Mapper;




import com.spring.jwt.dto.AddressDTO;
import com.spring.jwt.entity.Address;
import com.spring.jwt.entity.Application;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public static AddressDTO toAddressDTO(Address address) {
        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setAddressId(address.getAddressId());
        addressDTO.setStreetAddress(address.getStreetAddress());
        addressDTO.setTaluka(address.getTaluka());
        addressDTO.setDistrict(address.getDistrict());
        addressDTO.setState(address.getState());
        addressDTO.setPincode(address.getPincode());
        addressDTO.setApplicationId(address.getApplication() != null ? address.getApplication().getApplicationId() : null);

        return addressDTO;
    }

    public static Address toAddress(AddressDTO addressDTO) {
        Address address = new Address();

        address.setStreetAddress(addressDTO.getStreetAddress());
        address.setState(addressDTO.getState());
        address.setDistrict(addressDTO.getDistrict());
        address.setTaluka(addressDTO.getTaluka());
        address.setPincode(addressDTO.getPincode());

        // Set the application
        if (addressDTO.getApplicationId() != null) {
            Application application = new Application();
            application.setApplicationId(addressDTO.getApplicationId());
            address.setApplication(application);
        }

        return address;
    }
}

