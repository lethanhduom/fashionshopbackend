package com.management.shopfashion.service.impl;

import java.util.List;import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.management.shopfashion.dto.SizeDto;
import com.management.shopfashion.entity.Size;
import com.management.shopfashion.mapper.SizeMapper;
import com.management.shopfashion.repository.SizeRepo;
import com.management.shopfashion.service.SizeService;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class SizeServiceImpl implements SizeService {
	private SizeRepo sizeRepo;
	@Override
	public SizeDto createSize(SizeDto sizeDto) {
		Size sizeSave=sizeRepo.save(SizeMapper.MapSize(sizeDto));
		return SizeMapper.MapSizeDto(sizeSave);
	}
	@Override
	public SizeDto updateSize(SizeDto size,int id) {
		Size findSize=sizeRepo.findSizeById(id);
		findSize.setIsDelete(size.getIsDelete());
		findSize.setNameSize(size.getNameSize());
		Size saveSize=sizeRepo.save(findSize);
		return SizeMapper.MapSizeDto(saveSize);
	}
	@Override
	public List<SizeDto> getListSize() {
		return sizeRepo.findAll().stream().map(size->SizeMapper.MapSizeDto(size)).collect(Collectors.toList());
	}
	@Override
	public Page<SizeDto> getPageSize(int page, int size) {
		Page<Size>pageSize=sizeRepo.getPageSize(PageRequest.of(page, size));
		return pageSize.map(s->SizeMapper.MapSizeDto(s));
		
	}
	@Override
	public SizeDto getSize(int id) {
		Size size=sizeRepo.findSizeById(id);
		return SizeMapper.MapSizeDto(size);
	}

}
