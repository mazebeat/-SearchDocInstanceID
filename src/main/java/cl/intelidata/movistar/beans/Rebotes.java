package cl.intelidata.movistar.beans;

public class Rebotes {
	private String mail;
	private String date;
	private String codeError;
	private String descriptionError;
	private String docInstanceID;

	public Rebotes(String mail, String date, String codeError, String descriptionError, String docInstanceID) {
		this.mail = mail;
		this.date = date;
		this.codeError = codeError;
		this.descriptionError = descriptionError;
		this.docInstanceID = docInstanceID;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCodeError() {
		return codeError;
	}

	public void setCodeError(String codeError) {
		this.codeError = codeError;
	}

	public String getDescriptionError() {
		return descriptionError;
	}

	public void setDescriptionError(String descriptionError) {
		this.descriptionError = descriptionError;
	}

	public String getDocInstanceID() {
		return docInstanceID;
	}

	public void setDocInstanceID(String docInstanceID) {
		this.docInstanceID = docInstanceID;
	}

	@Override
	public String toString() {
		return "'" + mail + "','" + date + "','" + docInstanceID + "','" + codeError + "','" + descriptionError + "'";
	}
}
