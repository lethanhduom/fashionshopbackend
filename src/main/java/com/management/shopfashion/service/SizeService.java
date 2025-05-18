package com.management.shopfashion.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.management.shopfashion.dto.SizeDto;
import com.management.shopfashion.entity.Size;

@Service
public interface SizeService {
public SizeDto createSize(SizeDto size);
public SizeDto updateSize(SizeDto size,int id);
public List<SizeDto>getListSize();
public Page<SizeDto>getPageSize(int page,int size);
public SizeDto getSize(int id);

}
