package com.spring.jwt.controller;

import com.spring.jwt.Interfaces.DiscountUser;
import com.spring.jwt.dto.DiscountDto;
import com.spring.jwt.dto.Response;
import com.spring.jwt.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/Discount")
@RestController
public class DiscountController {
    @Autowired
    private DiscountUser discountUser;
    private DiscountService discountService;

    @PostMapping("/saveInformation")
    public ResponseEntity<Response> saveDiscount(@RequestBody DiscountDto discountDto) {
        try {
            DiscountDto saveDiscount = discountUser.saveDiscount(discountDto);
            Response response = new Response("SavedDiscount Added", saveDiscount, false);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Response response = new Response("Not Saved Discount", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        }

    }

    @GetMapping("/getById")
    public ResponseEntity<Response> getById(@RequestParam Integer id) {
        try {
            DiscountDto getId = discountUser.getByID(id);
            Response response = new Response("get by id succefully", getId, false);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Response response = new Response("not get it", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @GetMapping("/getAll")
    public ResponseEntity<Response> getAll() {
        try {
            List<DiscountDto> dis = discountUser.getall();
            Response response = new Response("getAll successfully", dis, false);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Response response = new Response("not get", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<Response> update( @RequestBody DiscountDto discountDto ,@RequestParam Integer id){
        try {


        DiscountDto update=discountUser.update(discountDto,id);
        Response response=new Response("Update Successfully",update,false);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);}
        catch (Exception e){
            Response response=new Response("not update succefully",e.getMessage(),true);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Response> deleteDiscount( @RequestParam Integer id){


            try {
                discountUser.deleteDiscount(id);

                return ResponseEntity.ok(new Response("Product deleted successfully", null, false));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new Response("An error occurred", e.getMessage(), true));
            }
        }
    }

//    public ResponseEntity<Response> Patchby
//}
