package it.unisa.studenti.anzelmo2.c.qr4labs.database.lab;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Lab")
public class Lab {

	@PrimaryKey
	@NonNull
	private String lid;
	@ColumnInfo(name = "name")
	private String name;
	@ColumnInfo(name = "info")
	private String info;
	@ColumnInfo(name = "site")
	private String site;

	public Lab(@NonNull String lid, String name, String info, String site) {
		this.lid=lid;
		this.name=name;
		this.info=info;
		this.site=site;
	}

	@NonNull
	public String getLid() {
		return lid;
	}

	public void setLid(@NonNull String lid) {
		this.lid = lid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}
}
