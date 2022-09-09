//$Id$
package model;

public class User {
	private int id;
	private String firstname;
	private String lastname;
	private String phone;
	private String email;
	private String username;
	private String password;
	private int dueAmount;
	private boolean isBanned;
	private int role;
	public User() {
		super();
	}
	public User(String firstname, String lastname, String phone, String email, String username, String password, int dueAmount, boolean isBanned, int role) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.phone = phone;
		this.email = email;
		this.username = username;
		this.password = password;
		this.dueAmount = dueAmount;
		this.isBanned = isBanned;
		this.role = role;
	}
	public User(int id, String firstname, String lastname, String phone, String email, String username, String password, int dueAmount, boolean isBanned, int role) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.phone = phone;
		this.email = email;
		this.username = username;
		this.password = password;
		this.dueAmount = dueAmount;
		this.isBanned = isBanned;
		this.role = role;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getDueAmount() {
		return dueAmount;
	}
	public void setDueAmount(int dueAmount) {
		this.dueAmount = dueAmount;
	}
	public boolean isBanned() {
		return isBanned;
	}
	public void setBanned(boolean isBanned) {
		this.isBanned = isBanned;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	
	
}
