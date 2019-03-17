package it.unisa.studenti.anzelmo2.c.qr4labs.database.project;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import it.unisa.studenti.anzelmo2.c.qr4labs.database.lab.Lab;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.user.User;

@Entity(tableName = "Project",
			foreignKeys = {@ForeignKey(entity = User.class, parentColumns = "uid", childColumns = "uid"),
								@ForeignKey(entity = Lab.class, parentColumns = "lid", childColumns = "lid")})
public class Project {

	@PrimaryKey
	@NonNull
	private String pid;
	@ColumnInfo(name = "name")
	private String name;
	@ColumnInfo(name = "description")
	private String description;
	@ColumnInfo(name = "uid")
	private String user;
	@ColumnInfo(name = "lid")
	private String lab;

	public Project(@NonNull String pid, String name, String description, String user, String lab) {
		this.pid = pid;
		this.name = name;
		this.description = description;
		this.user = user;
		this.lab = lab;
	}

	@NonNull
	public String getPid() {
		return pid;
	}

	public void setPid(@NonNull String pid) {
		this.pid = pid;
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

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getLab() {
		return lab;
	}

	public void setLab(String lab) {
		this.lab = lab;
	}
}
