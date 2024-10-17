package com.spring.jwt.Mapper;




import com.spring.jwt.dto.AddressDTO;
import com.spring.jwt.dto.ApplicationDTO;
import com.spring.jwt.dto.QualificationDTO;
import com.spring.jwt.entity.Address;
import com.spring.jwt.entity.Application;
import com.spring.jwt.entity.Qualification;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class ApplicationMapper {

    public static Application toApplicationEntity(ApplicationDTO applicationDTO) {
        Application application = new Application();
        application.setApplyFor(applicationDTO.getApplyFor());
        application.setApplicationId(applicationDTO.getApplicationId());
        application.setFirstName(applicationDTO.getFirstName());
        application.setLastName(applicationDTO.getLastName());
        application.setMiddleName(applicationDTO.getMiddleName());
        application.setGender(applicationDTO.getGender());
        application.setMailID(applicationDTO.getMailID());
        application.setMobileNo(applicationDTO.getMobileNo());
        application.setAlternateNo(applicationDTO.getAlternateNo());
        application.setDob(applicationDTO.getDob());
        application.setMaritalStatus(applicationDTO.getMaritalStatus());
        application.setAdharCard(applicationDTO.getAdharCard());
        application.setPanCardNo(applicationDTO.getPanCardNo());
        application.setOrganizationName(applicationDTO.getOrganizationName());
        application.setWorkingLocation(applicationDTO.getWorkingLocation());
        application.setPosition(applicationDTO.getPosition());
        application.setTypeOfEngagement(applicationDTO.getTypeOfEngagement());
        application.setExperienceYear(applicationDTO.getExperienceYear());
        application.setExperienceMonths(applicationDTO.getExperienceMonths());
        application.setExperienceDays(applicationDTO.getExperienceDays());
        applicationDTO.setResume(application.getResume());


        application.setQualifications(new ArrayList<>()); // Initialize the qualifications collection
        application.setAddresses(new ArrayList<>()); // Initialize the addresses collection

        // Map qualifications
        List<QualificationDTO> qualificationDTOs = applicationDTO.getQualifications();
        if (qualificationDTOs != null) {
            List<Qualification> qualifications = new ArrayList<>();
            for (QualificationDTO qualificationDTO : qualificationDTOs) {
                Qualification qualification = QualificationMapper.toQualification(qualificationDTO);
                qualification.setApplication(application); // Set the parent application in qualification
                qualifications.add(qualification);
            }
            application.setQualifications(qualifications);
        }

        // Map addresses if any
        // Assuming ApplicationDTO contains a list of AddressDTO
        List<AddressDTO> addressDTOs = applicationDTO.getAddresses();
        if (addressDTOs != null) {
            for (AddressDTO addressDTO : addressDTOs) {
                Address address = AddressMapper.toAddress(addressDTO);
                address.setApplication(application); // Set the parent application in address
                application.getAddresses().add(address);
            }
        }

        return application;
    }

    public static ApplicationDTO toApplicationDTO(Application application) {
        ApplicationDTO applicationDTO = new ApplicationDTO();
        applicationDTO.setApplyFor(application.getApplyFor());
        applicationDTO.setApplicationId(application.getApplicationId());
        applicationDTO.setFirstName(application.getFirstName());
        applicationDTO.setLastName(application.getLastName());
        applicationDTO.setMiddleName(application.getMiddleName());
        applicationDTO.setGender(application.getGender());
        applicationDTO.setMailID(application.getMailID());
        applicationDTO.setMobileNo(application.getMobileNo());
        applicationDTO.setAlternateNo(application.getAlternateNo());
        applicationDTO.setDob(application.getDob());
        applicationDTO.setMaritalStatus(application.getMaritalStatus());
        applicationDTO.setAdharCard(application.getAdharCard());
        applicationDTO.setPanCardNo(application.getPanCardNo());
        applicationDTO.setOrganizationName(application.getOrganizationName());
        applicationDTO.setWorkingLocation(application.getWorkingLocation());
        applicationDTO.setPosition(application.getPosition());
        applicationDTO.setTypeOfEngagement(application.getTypeOfEngagement());
        applicationDTO.setExperienceYear(application.getExperienceYear());
        applicationDTO.setExperienceMonths(application.getExperienceMonths());
        applicationDTO.setExperienceDays(application.getExperienceDays());
        applicationDTO.setSubmissionDate(application.getSubmissionDate());
        applicationDTO.setResume(applicationDTO.getResume());



        // Map qualifications
        List<Qualification> qualifications = application.getQualifications();
        if (qualifications != null) {
            List<QualificationDTO> qualificationDTOs = new ArrayList<>();
            for (Qualification qualification : qualifications) {
                QualificationDTO qualificationDTO = QualificationMapper.toQualificationDTO(qualification);
                qualificationDTOs.add(qualificationDTO);
            }
            applicationDTO.setQualifications(qualificationDTOs);
        }

        // Map addresses
        List<Address> addresses = application.getAddresses();
        if (addresses != null) {
            List<AddressDTO> addressDTOs = new ArrayList<>();
            for (Address address : addresses) {
                AddressDTO addressDTO = AddressMapper.toAddressDTO(address);
                addressDTOs.add(addressDTO);
            }
            applicationDTO.setAddresses(addressDTOs);
        }

        return applicationDTO;
    }


}


