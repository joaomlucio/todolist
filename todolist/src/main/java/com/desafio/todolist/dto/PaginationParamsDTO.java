package com.desafio.todolist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PaginationParamsDTO {
    @NotBlank
	@Pattern(regexp = "^-?\\d+(\\.\\d+)?$")
	private String page;

    @NotBlank
	@Pattern(regexp = "^-?\\d+(\\.\\d+)?$")
	private String size;

	@Pattern(flags = Pattern.Flag.CASE_INSENSITIVE, regexp = "DESC|ASC")
    private String sortDirection = "ASC";

	@Pattern(flags = Pattern.Flag.CASE_INSENSITIVE, regexp = "title|dueDate|createdAt|updatedAt")
	private String sortField = "dueDate";
}
