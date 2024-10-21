package com.spring.jwt.Interfaces;

import com.spring.jwt.dto.DiscountDto;

import java.util.List;


public interface DiscountUser {

    DiscountDto saveDiscount(DiscountDto discountDto);

    DiscountDto getByID(Integer id);

  List<DiscountDto> getall();

    DiscountDto update(DiscountDto discountDto, Integer id);

    void deleteDiscount(Integer id);
}
