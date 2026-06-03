package com.booking.service.Amenities.impl;

import com.booking.common.exception.AppException;
import com.booking.domain.dto.Amenities.AmenitiesDto;
import com.booking.domain.entity.Amenity;
import com.booking.repository.AmenitiesRepository;
import com.booking.service.Amenities.AmenitiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AmenitiesServiceImpl implements AmenitiesService {
    private final AmenitiesRepository amenitiesRepository;

    private AmenitiesDto toDto(Amenity amenity){
        AmenitiesDto amenitiesDto = new AmenitiesDto();
        amenitiesDto.setId(amenity.getId());
        amenitiesDto.setName(amenity.getName());
        return amenitiesDto;
    }

    @Override
    public List<AmenitiesDto> getAllAmenities() {
        try{
            return amenitiesRepository.findAll().stream().map(this::toDto).toList();
        } catch (Exception e) {
            throw new AppException(400, e.getMessage());
        }
    }
}
