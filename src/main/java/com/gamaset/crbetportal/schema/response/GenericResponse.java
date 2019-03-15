package com.gamaset.crbetportal.schema.response;

import java.util.List;

public class GenericResponse<T> {
	
	public GenericResponse(List<T> data) {
		setData(data);
	}
	
	private List<T> data;

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
