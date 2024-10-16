package com.spring.jwt.Interfaces;

import com.spring.jwt.dto.ApplicationDTO;
import com.spring.jwt.entity.Application;

import java.util.List;

public interface IFormUser {

    Object saveSvayamSavikaForm(ApplicationDTO applicationDTO);

    List<Application> getAllApplicationsSubmittedToday();

    List<ApplicationDTO> getAllApplication();
}
