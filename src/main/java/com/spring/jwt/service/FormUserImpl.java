package com.spring.jwt.service;

import com.spring.jwt.Interfaces.IFormUser;
import com.spring.jwt.Mapper.AddressMapper;
import com.spring.jwt.Mapper.ApplicationMapper;
import com.spring.jwt.Mapper.QualificationMapper;
import com.spring.jwt.dto.AddressDTO;
import com.spring.jwt.dto.ApplicationDTO;
import com.spring.jwt.dto.QualificationDTO;
import com.spring.jwt.entity.Address;
import com.spring.jwt.entity.Application;
import com.spring.jwt.entity.PaymentStatus;
import com.spring.jwt.entity.Qualification;
import com.spring.jwt.repository.AddressRepository;
import com.spring.jwt.repository.ApplicationRepository;
import com.spring.jwt.repository.QualificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

    @Service
    public class FormUserImpl implements IFormUser {

        @Autowired
        private ApplicationRepository applicationRepository;

        @Autowired
        private QualificationRepository qualificationRepository;

        @Autowired
        private AddressRepository addressRepository;

        @Autowired
        private AddressMapper addressMapper;

        @Autowired
        private QualificationMapper qualificationMapper;

        private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        private static final String MOBILE_REGEX = "^[6-9][0-9]{9}$";
        private static final String AADHAARCARD_REGEX = "^[2-9]{1}[0-9]{11}$";
        private static final String PANCARD_REGEX = "^[A-Z]{5}[0-9]{4}[A-Z]{1}$";


        private static boolean validateMobileNumber(String first_MobileNo) {
            if (first_MobileNo == null || !Pattern.matches(MOBILE_REGEX, first_MobileNo)) {
                throw new IllegalArgumentException("Invalid mobile number fort");
            }
            return true;
        }

        private static boolean validateMobileNumber1(String second_MobileNo) {
            if (!Pattern.matches(MOBILE_REGEX, second_MobileNo)) {
                throw new IllegalArgumentException("Invalid mobile number format");
            }
            return true;
        }

        private static boolean validateEmail(String mailId) {
            if (mailId == null || !Pattern.matches(EMAIL_REGEX, mailId)) {
                throw new IllegalArgumentException(("invalid email format"));
            }
            return true;
        }

        private Boolean validatePan(String pan) {
            if (pan == null || !Pattern.matches(PANCARD_REGEX, pan)) {
                throw new IllegalArgumentException("Invalid PAN card format");
            }
            return true;
        }

        private Boolean validateAadhaar(String aadhaar) {
            if (aadhaar == null || !Pattern.matches(AADHAARCARD_REGEX, aadhaar)) {
                throw new IllegalArgumentException("Invalid Aadhaar number format");
            }
            return true;
        }

    //    private Boolean validateDob(LocalDate dob) {
    //        if (dob == null) {
    //            throw new IllegalArgumentException("Date of birth cannot be null.");
    //        }
    //        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    //        try {
    //            LocalDate parsedDate = LocalDate.parse((CharSequence) dob, formatter);
    //        } catch (DateTimeParseException e) {
    //            throw new IllegalArgumentException("Invalid date of birth format or logical date.");
    //        }
    //        return true;
    //    }

        @Override
        public Object saveSvayamSavikaForm(ApplicationDTO applicationDTO, MultipartFile file) throws RuntimeException {

            applicationDTO.setPanCardNo(applicationDTO.getPanCardNo().toUpperCase()); // Convert PAN to uppercase

            if (applicationRepository.findByMailID(applicationDTO.getMailID()).isPresent()) {
                throw new IllegalArgumentException("Email ID is already in use.");
            }
            if (applicationRepository.findByAdharCard(applicationDTO.getAdharCard()).isPresent()) {
                throw new IllegalArgumentException("AadhaarCard no. is already in use.");
            }
            if (applicationRepository.findByPanCardNo(applicationDTO.getPanCardNo()).isPresent()) {
                throw new IllegalArgumentException("PanCard no. is already in use.");
            }

            if (validateEmail(applicationDTO.getMailID()) && validateMobileNumber(applicationDTO.getMobileNo())
                    && validateAadhaar(applicationDTO.getAdharCard()) && validatePan(applicationDTO.getPanCardNo())){
                // && validateDob(applicationDTO.getDob())) {
                if (applicationDTO.getAlternateNo() != null) {
                    validateMobileNumber(applicationDTO.getAlternateNo());
                }

                Application application = ApplicationMapper.toApplicationEntity(applicationDTO);

                // Process the resume file
                if (file != null && !file.isEmpty()) {
                    try {
                        application.setResume(file.getBytes()); // Convert MultipartFile to byte[]
                        application.setResumeName(file.getOriginalFilename()); // Set the resume name
                        application.setResumeType(file.getContentType()); // Set the resume type (MIME type)
                    } catch (IOException e) {
                        throw new RuntimeException("Error reading resume file: " + e.getMessage(), e);
                    }
                } else {
                    throw new RuntimeException("Resume is Mandatory");
                }

                if(application.getImage() == null){
                    application.setPaymentStatus(PaymentStatus.NO_PAYMENT);
                }

                // Save application entity
                Application savedApplication = applicationRepository.save(application);

                // Save qualifications
                List<QualificationDTO> qualificationDTOS = applicationDTO.getQualifications();
                if (qualificationDTOS != null) {
                    for (QualificationDTO qualificationDTO : qualificationDTOS) {
                        Qualification qualification = QualificationMapper.toQualification(qualificationDTO);

                        Optional<Qualification> existingQualification = qualificationRepository.findByApplicationAndStandardAndUniversityAndPassingYearAndPercentage(
                                savedApplication, qualification.getStandard(), qualification.getUniversity(),
                                qualification.getPassingYear(), qualification.getPercentage());

    //                    if (existingQualification.isEmpty()) {
    //                        qualification.setApplication(savedApplication);
    //                        qualificationRepository.save(qualification);
    //                    } else {
    //                        throw new RuntimeException("Duplicate qualification found, skipping save: " + qualificationDTO);
    //                    }
                    }
                } else {
                    throw new RuntimeException("Qualification is Mandatory");
                }

                // Save addresses
                List<AddressDTO> addressDTOs = applicationDTO.getAddresses();
                if (addressDTOs != null) {
                    for (AddressDTO addressDTO : addressDTOs) {
                        Address address = AddressMapper.toAddress(addressDTO);

                        Optional<Address> existingAddress = addressRepository.findByApplicationAndStreetAddressAndDistrictAndPincodeAndStateAndTaluka(
                                savedApplication, address.getStreetAddress(), address.getDistrict(), address.getPincode(), address.getState(), address.getTaluka());

    //                    if (existingAddress.isEmpty()) {
    //                        address.setApplication(savedApplication);
    //                        addressRepository.save(address);
    //                    } else {
    //                        throw new RuntimeException("Duplicate address found, skipping save: " + addressDTO);
    //                    }
                    }
                } else {
                    throw new RuntimeException("Address is Mandatory");
                }

                // Save the final application and return the DTO
                applicationRepository.save(savedApplication);
                return ApplicationMapper.toApplicationDTO(savedApplication);

            } else {
                throw new RuntimeException("Applicant not created due to invalid details");
            }
        }

        @Override
        public Object saveSvayamSavikaFormMarathi(ApplicationDTO applicationDTO, MultipartFile file) throws RuntimeException {

//            applicationDTO.setPanCardNo(applicationDTO.getPanCardNo().toUpperCase()); // Convert PAN to uppercase
//
//            if (applicationRepository.findByMailID(applicationDTO.getMailID()).isPresent()) {
//                throw new IllegalArgumentException("Email ID is already in use.");
//            }
//            if (applicationRepository.findByAdharCard(applicationDTO.getAdharCard()).isPresent()) {
//                throw new IllegalArgumentException("AadhaarCard no. is already in use.");
//            }
//            if (applicationRepository.findByPanCardNo(applicationDTO.getPanCardNo()).isPresent()) {
//                throw new IllegalArgumentException("PanCard no. is already in use.");
//            }
//
//            if (validateEmail(applicationDTO.getMailID()) && validateMobileNumber(applicationDTO.getMobileNo())
//                    && validateAadhaar(applicationDTO.getAdharCard()) && validatePan(applicationDTO.getPanCardNo())){
//                // && validateDob(applicationDTO.getDob())) {
//                if (applicationDTO.getAlternateNo() != null) {
//                    validateMobileNumber(applicationDTO.getAlternateNo());
//                }

                Application application = ApplicationMapper.toApplicationEntity(applicationDTO);

                // Process the resume file
                if (file != null && !file.isEmpty()) {
                    try {
                        application.setResume(file.getBytes()); // Convert MultipartFile to byte[]
                        application.setResumeName(file.getOriginalFilename()); // Set the resume name
                        application.setResumeType(file.getContentType()); // Set the resume type (MIME type)
                    } catch (IOException e) {
                        throw new RuntimeException("Error reading resume file: " + e.getMessage(), e);
                    }
                } else {
                    throw new RuntimeException("Resume is Mandatory");
                }

                if(application.getImage() == null){
                    application.setPaymentStatus(PaymentStatus.NO_PAYMENT);
                }

                // Save application entity
                Application savedApplication = applicationRepository.save(application);

                // Save qualifications
                List<QualificationDTO> qualificationDTOS = applicationDTO.getQualifications();
                if (qualificationDTOS != null) {
                    for (QualificationDTO qualificationDTO : qualificationDTOS) {
                        Qualification qualification = QualificationMapper.toQualification(qualificationDTO);

                        Optional<Qualification> existingQualification = qualificationRepository.findByApplicationAndStandardAndUniversityAndPassingYearAndPercentage(
                                savedApplication, qualification.getStandard(), qualification.getUniversity(),
                                qualification.getPassingYear(), qualification.getPercentage());

                        //                    if (existingQualification.isEmpty()) {
                        //                        qualification.setApplication(savedApplication);
                        //                        qualificationRepository.save(qualification);
                        //                    } else {
                        //                        throw new RuntimeException("Duplicate qualification found, skipping save: " + qualificationDTO);
                        //                    }
                    }
                } else {
                    throw new RuntimeException("Qualification is Mandatory");
                }

                // Save addresses
                List<AddressDTO> addressDTOs = applicationDTO.getAddresses();
                if (addressDTOs != null) {
                    for (AddressDTO addressDTO : addressDTOs) {
                        Address address = AddressMapper.toAddress(addressDTO);

                        Optional<Address> existingAddress = addressRepository.findByApplicationAndStreetAddressAndDistrictAndPincodeAndStateAndTaluka(
                                savedApplication, address.getStreetAddress(), address.getDistrict(), address.getPincode(), address.getState(), address.getTaluka());

                        //                    if (existingAddress.isEmpty()) {
                        //                        address.setApplication(savedApplication);
                        //                        addressRepository.save(address);
                        //                    } else {
                        //                        throw new RuntimeException("Duplicate address found, skipping save: " + addressDTO);
                        //                    }
                    }
                } else {
                    throw new RuntimeException("Address is Mandatory");
                }

                // Save the final application and return the DTO
                applicationRepository.save(savedApplication);
                return ApplicationMapper.toApplicationDTO(savedApplication);
//
//            } else {
//                throw new RuntimeException("Applicant not created due to invalid details");
//            }
        }

        @Override
        public List<Application> getAllApplicationsSubmittedToday() {
            return applicationRepository.findBySubmissionDate(LocalDate.now());
        }

        @Override
        public List<ApplicationDTO> getAllApplication() {
            List<Application> applicationList = applicationRepository.findAll();
            List<ApplicationDTO> applicationDTOList = new ArrayList<>();
            for (Application application : applicationList) {
                applicationDTOList.add(ApplicationMapper.toApplicationDTO(application));
            }
            return applicationDTOList;
        }

        @Override
        public Object saveImage(Integer id, MultipartFile image) throws RuntimeException, IOException {
            Application existingApplication = applicationRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Application not found with ID: " + id));

            byte[] imageBytes = image.getBytes();
            existingApplication.setImage(imageBytes);

            existingApplication.setPaymentStatus(PaymentStatus.PENDING);
            return applicationRepository.save(existingApplication);
        }

        @Override
        public List<Application> getByDate(LocalDate date) {
            return applicationRepository.findByDate(LocalDate.now());
        }

        @Override
        public List<Application> getPendingApplications() {
            return applicationRepository.findByPaymentStatus(PaymentStatus.PENDING);
        }

        @Override
        public Application updatePaymentStatus(Integer id, Boolean adminApproved) {
            Application application = applicationRepository.findById(id).orElseThrow();
            if (adminApproved) {
                application.setPaymentStatus(PaymentStatus.SUCCESS); // No need for Application. here if imported
            } else {
                application.setPaymentStatus(PaymentStatus.PENDING); // No need for Application. here if imported
                applicationRepository.deleteById(id);
                new RuntimeException("Pending Applicant Deleted successfully ");
            }
            return applicationRepository.save(application);
        }
    }
