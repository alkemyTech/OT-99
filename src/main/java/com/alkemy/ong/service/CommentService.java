package com.alkemy.ong.service;

import java.util.List;

import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.dto.CommentDtoResponse;
import com.alkemy.ong.dto.CommentDtoSave;
import com.alkemy.ong.exception.NotFoundException;

public interface CommentService {
    
	List<CommentDto> getAll();

	CommentDtoResponse save(CommentDtoSave commentDtoSave) throws NotFoundException;
        
        List<CommentDtoResponse> getCommentbyNewsId(Long id) throws NotFoundException;
}