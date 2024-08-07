package com.dd.bikes.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestMailForm {

	private String name;
	private String email;
	private String subject;
	private String message;
}
