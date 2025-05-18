package com.management.shopfashion.mapper;

import com.management.shopfashion.dto.ColorDto;
import com.management.shopfashion.entity.Color;

public class ColorMapper {
	public static ColorDto MapColorDto(Color color) {
		if(color==null) 
			return null;
		ColorDto colorDto=new ColorDto();
		colorDto.setId_color(color.getId_color());
		colorDto.setCodeColor(color.getCodeColor());
		colorDto.setIsDelete(color.getIsDelete());
		colorDto.setNameColor(color.getNameColor());
		return colorDto;
	}
	public static Color MapColor(ColorDto colorDto) {
		if(colorDto==null)
			return null;
		Color color=new Color();
		color.setId_color(colorDto.getId_color());
		color.setNameColor(colorDto.getNameColor());
		color.setIsDelete(colorDto.getIsDelete());
		color.setCodeColor(colorDto.getCodeColor());
		return color;
	}
}
