package com.gamaset.crbetportal.repository.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tipo_evento")
public class EventTypeModel extends Auditable implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "descricao")
	private String description;

	@Column(name = "ativo")
	private boolean active;

	public EventTypeModel() {
	}

	public EventTypeModel(Long id, String description, boolean active) {
		this.id = id;
		this.description = description;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return description;
	}

	public void setName(String name) {
		this.description = name;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
