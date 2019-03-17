package it.unisa.studenti.anzelmo2.c.qr4labs.utility;

public class WebResponse {
	private boolean inserted;

	public WebResponse(boolean inserted) {
		this.inserted = inserted;
	}

	public boolean isInserted() {
		return inserted;
	}

	public void setInserted(boolean inserted) {
		this.inserted = inserted;
	}
}
