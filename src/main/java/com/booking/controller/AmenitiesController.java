package com.booking.controller;

import com.booking.common.response.BaseResponse;
import com.booking.domain.dto.Amenities.AmenitiesDto;
import com.booking.service.Amenities.AmenitiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AmenitiesController {
    private final AmenitiesService amenitiesService;

    @GetMapping("/amenities")
    public BaseResponse<List<AmenitiesDto>> getAmenities(){
        return BaseResponse.success(amenitiesService.getAllAmenities());
    }
}
