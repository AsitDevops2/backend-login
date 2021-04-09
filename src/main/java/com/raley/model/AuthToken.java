package com.raley.model;

/**
 * @author abhay.thakur
 *
 */
public class AuthToken {

	private String token;
	private int id;
	private String email;
	private String firstName;
	private String lastName;
	private long mobile;
	private int pin;
	private String country;
	private String state;
	private String city;
	private String dept;
	private String addr1;
	private String addr2;
	private String role;
	private int parent;

	public AuthToken() {

	}

	public AuthToken(String token, int id, String email, long mobile, String firstName, String lastName, int pin,
			String country, String state, String city, String dept, String addr1, String addr2,String role,int parent) {
		this.token = token;
		this.id = id;
		this.email = email;
		this.mobile = mobile;
		this.firstName = firstName;
		this.lastName = lastName;
		this.pin = pin;
		this.country = country;
		this.state = state;
		this.city = city;
		this.dept = dept;
		this.addr1 = addr1;
		this.addr2 = addr2;
		this.role=role;
		this.parent=parent;
	}

	public AuthToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public long getMobile() {
		return mobile;
	}

	public void setMobile(long mobile) {
		this.mobile = mobile;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getRole() {
		return role;
	}

	public String getAddr1() {
		return addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public String getAddr2() {
		return addr2;
	}

	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

}
