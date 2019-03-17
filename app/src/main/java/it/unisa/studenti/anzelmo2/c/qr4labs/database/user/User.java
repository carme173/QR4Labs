package it.unisa.studenti.anzelmo2.c.qr4labs.database.user;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import it.unisa.studenti.anzelmo2.c.qr4labs.database.lab.Lab;

@Entity(tableName = "User")
public class User {

	@PrimaryKey
	@NonNull
	private String uid;
	@ColumnInfo(name = "password")
	private String password;
	@SerializedName("first_name")
	@ColumnInfo(name = "first_name")
	private String firstName;
	@SerializedName("last_name")
	@ColumnInfo(name = "last_name")
	private String lastName;
	private String email;
	private String lid;
	@SerializedName("name")
	private String role;

	public User(@NonNull String uid, String password, String firstName, String lastName, String email, String lid, String role) {
		this.uid = uid;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.lid = lid;
		this.role = role;
	}

	public User() {

	}

	@NonNull
	public String getUid() {
		return uid;
	}

	public void setUid(@NonNull String uid) {
		this.uid = uid;
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

	@NonNull
	public String getPassword() {
		return password;
	}

	public void setPassword(@NonNull String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getLid() {
		return lid;
	}

	public void setLid(String lid) {
		this.lid = lid;
	}

	public String getFullName() {
		return (firstName+" "+lastName);
	}

	public String getUserDetails() {
		return (getFullName()+" "+role);
	}

	@Override
	public String toString() {
		return "User{" +
				 "uid='" + uid + '\'' +
				 ", password='" + password + '\'' +
				 ", firstName='" + firstName + '\'' +
				 ", lastName='" + lastName + '\'' +
				 ", email='" + email + '\'' +
				 ", lid='" + lid + '\'' +
				 ", role='" + role + '\'' +
				 '}';
	}
}
