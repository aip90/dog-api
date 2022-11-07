package com.example.dogapi.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@ToString
public class BaseEntity {

	@Temporal(TemporalType.TIMESTAMP)
	@JsonIgnore
	private Date createdDate;

	@JsonIgnore
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonIgnore
	private Date updatedDate;

	@JsonIgnore
	private String updatedBy;
}
