package com.spring.jwt.service;

import com.spring.jwt.Interfaces.DiscountUser;
import com.spring.jwt.config.MapperConfig;
import com.spring.jwt.dto.DiscountDto;
import com.spring.jwt.entity.Discount;
import com.spring.jwt.repository.DiscountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiscountService implements DiscountUser {
    @Autowired
    private DiscountRepository discountRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public DiscountDto saveDiscount(DiscountDto discountDto) {

        Discount discount = modelMapper.map(discountDto, Discount.class);
        discountRepository.save(discount);
        return modelMapper.map(discount, DiscountDto.class);
    }

    @Override
    public DiscountDto getByID(Integer id) {
        Discount dis = discountRepository.findById(id).orElse(null);
        DiscountDto disDto = modelMapper.map(dis, DiscountDto.class);
        return disDto;

    }

    @Override
    public List<DiscountDto> getall() {
        List<Discount> dis = discountRepository.findAll();
        List<DiscountDto> dib = new ArrayList();
        for (Discount dil : dis) {
            dib.add(modelMapper.map(dil, DiscountDto.class));
        }
        return dib;
    }

    @Override
    public DiscountDto update(DiscountDto discountDto, Integer id) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));

        if (discountDto.getDiscountName() != null) {
            discount.setDiscountName(discountDto.getDiscountName());
        }
        if (discountDto.getDiscountValue() != null) {
            discount.setDiscountValue(discountDto.getDiscountValue());
        }

        Discount updatedDiscount = discountRepository.save(discount);
        return modelMapper.map(updatedDiscount, DiscountDto.class);
    }


    @Override
    public void deleteDiscount(Integer id) {
        Discount existingDiscount = discountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
        discountRepository.delete(existingDiscount);


    }
}