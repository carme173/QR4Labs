package it.unisa.studenti.anzelmo2.c.qr4labs.database.cache;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.Date;

import it.unisa.studenti.anzelmo2.c.qr4labs.database.alert.DateConverter;

@Entity(tableName = "Cache")
@TypeConverters({DateConverter.class})
public class Cache {

	@NonNull
	@PrimaryKey
	private String cid;
	private Date lastUpdate;

	public Cache() {
	}

	public Cache(@NonNull String cid, Date lastUpdate) {
		this.cid = cid;
		this.lastUpdate = lastUpdate;
	}

	@NonNull
	public String getCid() {
		return cid;
	}

	public void setCid(@NonNull String cid) {
		this.cid = cid;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
}
