package com.management.shopfashion.mapper;

import com.management.shopfashion.dto.SizeDto;
import com.management.shopfashion.entity.Size;

public class SizeMapper {
 public static SizeDto MapSizeDto(Size size) {
	 if(size==null)
		 return null;
	 SizeDto sizeDto=new SizeDto();
	 sizeDto.setId_size(size.getId_size());
	 sizeDto.setNameSize(size.getNameSize());
	 sizeDto.setIsDelete(size.getIsDelete());
	 return sizeDto;
 }
 public static Size MapSize(SizeDto sizeDto) {
	 if(sizeDto==null)
		 return null;
	 Size size=new Size();
	 size.setId_size(sizeDto.getId_size());
	 size.setNameSize(sizeDto.getNameSize());
	 size.setIsDelete(sizeDto.getIsDelete());
	 return size;
 }
}

