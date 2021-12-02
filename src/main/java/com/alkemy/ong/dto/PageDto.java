package com.alkemy.ong.dto;

import java.util.List;
import lombok.Data;

@Data
public class PageDto<T> {

	private Integer totalPages;
	private String nextPage;
	private String previousPage;
	private List<T> list;

}
