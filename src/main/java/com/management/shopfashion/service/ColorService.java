package com.management.shopfashion.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.management.shopfashion.dto.ColorDto;
import com.management.shopfashion.entity.Color;

@Service
public interface ColorService {
public ColorDto createColor(ColorDto color);
public ColorDto updateColor(ColorDto color,int id);
public List<ColorDto> getListColor();
public Page<ColorDto>getColorDisplay(int page,int size);
public ColorDto getItem(int id);
}
