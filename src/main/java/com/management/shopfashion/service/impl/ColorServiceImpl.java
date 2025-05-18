package com.management.shopfashion.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.management.shopfashion.dto.ColorDto;
import com.management.shopfashion.entity.Color;
import com.management.shopfashion.mapper.ColorMapper;
import com.management.shopfashion.repository.ColorRepo;
import com.management.shopfashion.service.ColorService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ColorServiceImpl implements ColorService{
	private ColorRepo colorRepo;
	@Override
	public ColorDto createColor(ColorDto color) {
		Color saveColor=colorRepo.save(ColorMapper.MapColor(color));
		return ColorMapper.MapColorDto(saveColor);
	}
	@Override
	public ColorDto updateColor(ColorDto color, int id) {
		Color findColor=colorRepo.findByIdColor(id);
		findColor.setIsDelete(color.getIsDelete());
		findColor.setNameColor(color.getNameColor());
		findColor.setCodeColor(color.getCodeColor());
		Color saveColor=colorRepo.save(findColor);
		return ColorMapper.MapColorDto(saveColor);
	}
	@Override
	public List<ColorDto> getListColor() {
		List<Color> listColor=colorRepo.findAll();
		return listColor.stream().map(color->ColorMapper.MapColorDto(color)).collect(Collectors.toList());
	}
	@Override
	public Page<ColorDto> getColorDisplay(int page,int size) {
		Page<Color>list=colorRepo.getlistColor(PageRequest.of(page, size));
		return  list.map(color->ColorMapper.MapColorDto(color));
	}
	@Override
	public ColorDto getItem(int id) {
		Color color=colorRepo.getById(id);
		return ColorMapper.MapColorDto(color);
	}

}
