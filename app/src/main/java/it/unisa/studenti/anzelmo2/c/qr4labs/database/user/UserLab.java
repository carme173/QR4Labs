package it.unisa.studenti.anzelmo2.c.qr4labs.database.user;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import it.unisa.studenti.anzelmo2.c.qr4labs.database.lab.Lab;

@Entity(tableName = "UserLab",
			foreignKeys = {@ForeignKey(entity = User.class, parentColumns = "uid", childColumns = "uid"),
								@ForeignKey(entity = Lab.class, parentColumns = "lid", childColumns = "lid")})
public class UserLab {
	@NonNull
	@PrimaryKey(autoGenerate = true)
	private int ulid;
	private String uid;
	private String lid;
	private int ruid;

	public UserLab(String uid, String lid, int ruid) {
		this.uid = uid;
		this.lid = lid;
		this.ruid = ruid;
	}

	@NonNull
	public int getUlid() {
		return ulid;
	}

	public void setUlid(@NonNull int ulid) {
		this.ulid = ulid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getLid() {
		return lid;
	}

	public void setLid(String lid) {
		this.lid = lid;
	}

	public int getRuid() {
		return ruid;
	}

	public void setRuid(int ruid) {
		this.ruid = ruid;
	}
}
