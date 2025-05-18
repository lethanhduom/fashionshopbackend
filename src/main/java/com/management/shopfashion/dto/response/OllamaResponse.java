package com.management.shopfashion.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OllamaResponse {
	 private String response;
	 private String sql_query;
	    private List<DataOllamaResponse> productResponse;
	    private String need_clarification;
	    
}
