package com.zviproject.component.entity;

public class ReturnedId {
	private int id;

	private Status status;

	public int getId() {
		return id;
	}

	// public ReturnedId() {
	// }

	public ReturnedId(int id, Status status) {
		this.id = id;
		this.status = status;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}