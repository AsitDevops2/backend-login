package com.raley.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.raley.model.User;

public class Category {
	
	private String _id;
	private String name;
	private String description;
	private String userId;
	@JsonIgnore
	private String __v;
	private User parent;
	
	public Category() {
		super();
	}
	
	public Category(String _id, String name, String description, String userId, String __v, User parent) {
		super();
		this._id = _id;
		this.name = name;
		this.description = description;
		this.userId = userId;
		this.__v = __v;
		this.parent = parent;
	}

	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String get__v() {
		return __v;
	}
	public void set__v(String __v) {
		this.__v = __v;
	}
	public User getParent() {
		return parent;
	}
	public void setParent(User parent) {
		this.parent = parent;
	}

}
