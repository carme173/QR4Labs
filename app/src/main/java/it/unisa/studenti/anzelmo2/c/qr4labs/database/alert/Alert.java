package it.unisa.studenti.anzelmo2.c.qr4labs.database.alert;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import it.unisa.studenti.anzelmo2.c.qr4labs.database.lab.Lab;

@Entity(tableName = "Alert",
		 foreignKeys = @ForeignKey(entity = Lab.class, parentColumns = "lid", childColumns = "lab")
)
@TypeConverters({DateConverter.class})
public class Alert {

	@PrimaryKey(autoGenerate = true)
	@NonNull
	private int aid;
	@SerializedName("title")
	@ColumnInfo(name = "title")
	private String title;
	@SerializedName("text")
	@ColumnInfo(name = "text")
	private String text;
	@SerializedName("pub_date")
	private String publicationString;
	@ColumnInfo(name = "pub_date")
	private Date publicationDate;
	@SerializedName("pub_exp")
	private String expirationString;
	@ColumnInfo(name = "exp_date")
	private Date expirationDate;
	@SerializedName("author")
	@ColumnInfo(name = "author")
	private String author;
	@ColumnInfo(name = "lab")
	private String lab;
	@Ignore
	private boolean expired;

	@Ignore
	public Alert(String title, String text, Date publicationDate, Date expirationDate, String author, String lab) {
		this.title = title;
		this.text = text;
		publicationString=convertToString(publicationDate);
		this.publicationDate = publicationDate;
		expirationString=convertToString(expirationDate);
		this.expirationDate = expirationDate;
		this.author = author;
		this.lab = lab;
		isExpired();
	}

	public Alert(String title, String text, String publicationString, Date publicationDate,
					 String expirationString, Date expirationDate, String author, String lab) {
		this.title = title;
		this.text = text;
		this.author = author;
		this.lab = lab;

		if (publicationDate==null && expirationDate==null) {
			this.publicationDate=convertToDate(publicationString);
			this.publicationString=convertToString(this.publicationDate);
			this.expirationDate=convertToDate(expirationString);
			this.expirationString=convertToString(this.expirationDate);
		} else {
			this.publicationString=convertToString(publicationDate);
			this.publicationDate = publicationDate;
			this.expirationString=convertToString(expirationDate);
			this.expirationDate = expirationDate;
		}
		isExpired();
	}

	public void update(String title, String text, String publicationString, String expirationString,
							 String author) {
		this.title=title;
		this.text=text;
		this.publicationString=publicationString;
		this.publicationDate=convertToDate(publicationString);
		this.expirationString=expirationString;
		this.expirationDate=convertToDate(expirationString);
		this.author=author;
	}

	@NonNull
	public int getAid() {
		return aid;
	}

	public void setAid(@NonNull int aid) {
		this.aid = aid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getLab() {
		return lab;
	}

	public void setLab(String lab) {
		this.lab = lab;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPublicationString() {
		return publicationString;
	}

	public void setPublicationString(String publicationString) {
		this.publicationString = publicationString;
	}

	public String getExpirationString() {
		return expirationString;
	}

	public void setExpirationString(String expirationString) {
		this.expirationString = expirationString;
	}

	public boolean isExpired() {
		Calendar cal=Calendar.getInstance();
		Date today=cal.getTime();

		expired=!(expirationDate.after(today));

		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	private String convertToString(Date date) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		return (sdf.format(date));
	}

	private Date convertToDate(String string) {
		SimpleDateFormat sdf;

		if (string.matches("\\d{4}-\\d{1,2}-\\d{1,2}"))
			sdf=new SimpleDateFormat("yyyy-M-d");
		else
			sdf=new SimpleDateFormat("d/M/yyyy");

		try {
			return (sdf.parse(string));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return new Date();
	}

	@Override
	public String toString() {
		return "Alert{" +
				 "aid=" + aid +
				 ", title='" + title + '\'' +
				 ", text='" + text + '\'' +
				 ", publicationString='" + publicationString + '\'' +
				 ", publicationDate=" + publicationDate +
				 ", expirationString='" + expirationString + '\'' +
				 ", expirationDate=" + expirationDate +
				 ", author='" + author + '\'' +
				 ", lab='" + lab + '\'' +
				 ", expired=" + expired +
				 '}';
	}
}
