package com.gamaset.crbetportal.repository.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@MappedSuperclass
public class Auditable {

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@CreationTimestamp
	@Column(name = "create_at", nullable = false)
	private LocalDateTime createAt;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@UpdateTimestamp
	@Column(name = "update_at", nullable = false)
	private LocalDateTime updateAt;

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}

	public LocalDateTime getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(LocalDateTime updateAt) {
		this.updateAt = updateAt;
	}

}
