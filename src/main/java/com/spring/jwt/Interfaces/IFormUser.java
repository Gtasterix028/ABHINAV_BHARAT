package com.spring.jwt.Interfaces;

import com.spring.jwt.dto.ApplicationDTO;
import com.spring.jwt.entity.Application;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IFormUser {


    List<Application> getAllApplicationsSubmittedToday();

    List<ApplicationDTO> getAllApplication();

    Object saveImage(Integer id, MultipartFile image) throws IOException;

    Object saveSvayamSavikaForm(ApplicationDTO applicationDTO, MultipartFile file);
}
