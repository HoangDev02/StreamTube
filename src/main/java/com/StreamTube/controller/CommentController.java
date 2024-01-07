package com.StreamTube.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.StreamTube.configs.CustomUserDetails;
import com.StreamTube.dtos.CommentDTO;
import com.StreamTube.dtos.UserDTO;
import com.StreamTube.mappers.CommentMapper;
import com.StreamTube.mappers.UserMapper;
import com.StreamTube.models.Comment;
import com.StreamTube.services.CommentService;
import com.StreamTube.services.UserService;


@RestController
@RequestMapping("/api/comments")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private CommentMapper commentMapper;

	@GetMapping("/{id}")
	public CommentDTO getCommentById(@PathVariable("id") Integer id) {
		return commentMapper.toDTO(commentService.getCommentById(id));
	}

	@GetMapping("/video/{videoId}")
	public List<CommentDTO> getAllCommentsFromVideo(@PathVariable("videoId") Integer videoId) {
		return commentMapper.toDTOList(commentService.getAllCommentByVideoId(videoId));
	}

	@PostMapping
	public CommentDTO createComment(@RequestBody CommentDTO commentDTO) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails  currentUserDetails= (CustomUserDetails) authentication.getPrincipal();
	    UserDTO user = userMapper.toDTO(userService.getUserById(currentUserDetails.getUser().getId())) ;
	    commentDTO.setUserId(user.getId());
		return commentMapper.toDTO(commentService.createComment(commentMapper.toModel(commentDTO)));
	}

	@PutMapping("/{id}")
	public ResponseEntity<CommentDTO> updateComment(@PathVariable("id") Integer id, @RequestBody CommentDTO commentDTO) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails currentUserDetails = (CustomUserDetails) authentication.getPrincipal();
	    Integer currentUserId = currentUserDetails.getUser().getId();

	    if (commentService.isOwner(id, currentUserId)) {
	        Comment updatedComment = commentService.updateComment(id, commentMapper.toModel(commentDTO));
	        return ResponseEntity.ok(commentMapper.toDTO(updatedComment));
	    } else {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	    }
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteComment(@PathVariable("id") Integer id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails currentUserDetails = (CustomUserDetails) authentication.getPrincipal();
	    Integer currentUserId = currentUserDetails.getUser().getId();

	    if (commentService.isOwner(id, currentUserId)) {
	        commentService.deleteComment(id);
	        return ResponseEntity.noContent().build();
	    } else {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	    }
	}


}