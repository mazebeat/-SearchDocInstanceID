package cl.intelidata.movistar.beans;

import org.jam.superutils.FastFileTextReader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SearchUtils {
	private int          errores;
	private Date         filedate;
	private String       dirIn;
	private String       dirOut;
	private String       docInstanceID;
	private String       date;
	private String       line;
	private String       mail;
	private String       codeError;
	private String       descriptionError;
	private List<String> listFiles;

	public SearchUtils(String dirIn, String dirOut) {
		this.dirIn = dirIn;
		this.dirOut = dirOut;
		this.errores = 0;
		this.listFiles = new ArrayList<String>();
		this.docInstanceID = "";
		this.date = "";
		this.line = "";
		this.mail = "";
		this.codeError = "";
		this.descriptionError = "";
		this.filedate = new Date();
	}

	public java.util.Date getFiledate() {
		return filedate;
	}

	public void setFiledate(java.util.Date filedate) {
		this.filedate = filedate;
	}

	public String getDirIn() {
		return dirIn;
	}

	public void setDirIn(String dirIn) {
		this.dirIn = dirIn;
	}

	public String getDirOut() {
		return dirOut;
	}

	public void setDirOut(String dirOut) {
		this.dirOut = dirOut;
	}

	public List<String> getListFiles() {
		return listFiles;
	}

	public void setListFiles(List<String> listFiles) {
		this.listFiles = listFiles;
	}

	public String getDocInstanceID() {
		return docInstanceID;
	}

	public void setDocInstanceID(String docInstanceID) {
		this.docInstanceID = docInstanceID;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
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

	public int getErrores() {
		return errores;
	}

	public void setErrores(int errores) {
		this.errores = errores;
	}

	public void process() {
		List<String> out = new ArrayList<String>();
		String line = "";
		Boolean pass = false;

		this.verifyDirectory();

		MessageUtils.debug("LISTA DE ARCHIVOS A PROCESAR\r\n\t\t\t" + this.getListFiles().toString());
		for (String file : this.getListFiles()) {
			try {
				MessageUtils.info("-------------------------------------------------------------------------");
				MessageUtils.info("PROCESANDO ARCHIVO: " + file);
				FastFileTextReader ffr = new FastFileTextReader(file, FastFileTextReader.UTF_8, 1024 * 40);
				while ((line = ffr.readLine()) != null) {
					if (line.contains(Text.ERROR_BDD)) {
						pass = this.verifyBddError(line);
					} else if (line.contains(Text.ERROR_CODE) && pass) {
						this.verityCodeError(line);
					} else if (line.contains(Text.ERROR_DESCRIPTION) && pass) {
						this.verifyDescError(line);
						Rebotes rebote = new Rebotes(this.getMail(), this.getDate(), this.getCodeError(), this.getDescriptionError(), this.getDocInstanceID());
						out.add(rebote.toString());
						// File f = new File(file);
						// this.writeLine(rebote.toString(), this.getDirOut().concat(File.separator).concat(f.getName()));
						this.writeLine(rebote.toString(), this.getDirOut().concat(File.separator).concat("ResultLog_" + Text.DATE_FORMAT.format(this.getFiledate())).concat(".txt"));
						pass = false;
					}
				}
				MessageUtils.info("DOCUMENTOS ENCONTRADOS: " + out.size());
				MessageUtils.info("DOCUMENTOS ENCONTRADOS SIN DATOS: " + this.getErrores() + " [SIN MAIL/DOCINSTANCEID]");
				this.setErrores(0);
				out.clear();
			} catch (Exception e) {
				MessageUtils.error(e.getMessage());
			}
		}
		MessageUtils.info("-------------------------------------------------------------------------");
		MessageUtils.info("ARCHIVO GENERADO DE SALIDA: " + this.getDirOut().concat(File.separator).concat("ResultLog_" + Text.DATE_FORMAT.format(this.getFiledate())).concat(".txt"));
	}

	private void verifyDirectory() {
		MessageUtils.debug("OBTENIENDO LISTADO DE ARCHIVOS DEL DIRECTORIO: " + this.getDirIn());
		this.setListFiles(FileUtils.readDirectory(this.getDirIn(), Text.LOG_EXT));
		if (this.getListFiles().size() <= 0) {
			MessageUtils.error("NO SE ENCONTRARON ARCHIVOS EN EL DIRECTORIO: " + this.getDirIn());
			System.exit(1);
		}

	}

	private String getAllMail(java.util.List<String> in) {
		String out = "";
		for (String v : in) {
			out += v.trim() + ".";
		}
		if (out.endsWith(".")) {
			int l = out.length();
			out = out.substring(0, l - 1);
		}
		return this.deleteDodAndComma(out).trim();
	}

	private Boolean writeLine(String line, String pathOut) throws IOException {
		String path = "";
		FileWriter fichero = null;
		PrintWriter pw = null;
		try {
			// this.getDirOut().concat(File.separator).concat("ResumeLogs.txt");
			path = pathOut;
			File f = new File(path);
			if (f.exists()) {
				fichero = new FileWriter(path, true);
			} else {
				fichero = new FileWriter(path);
			}
			pw = new PrintWriter(fichero);
			pw.println(line);

			return true;
		} catch (Exception e) {
			MessageUtils.error(e.getMessage());
		} finally {
			try {
				if (null != fichero) {
					fichero.close();
				}
			} catch (Exception e2) {
				MessageUtils.error(e2.getMessage());
			}
		}

		return false;
	}

	private String deleteDodAndComma(String a) {
		if (a.trim().startsWith(":")) {
			return a.trim().replace(":", "");
		}
		return a.trim();
	}

	private Boolean verifyBddError(String line) {
		if (line.contains(Text.ERROR_BDD_SEPARATOR)) {
			List<String> res = new ArrayList(Arrays.asList(line.split(Text.ERROR_BDD_SEPARATOR)));
			List<String> res2;
			if (res.size() > 1) {
				res2 = new ArrayList(Arrays.asList(res.get(1).split("\\.")));
			} else {
				res2 = new ArrayList(Arrays.asList(res.get(0).split("\\.")));
			}

			int lenght = res2.size();
			String docid = this.deleteDodAndComma(res2.get(lenght - 1));
			if (docid.contains(" - null")) {
				docid = docid.replace(" - null", "");
			}
			this.setDocInstanceID(docid);
			res2.remove(lenght - 1);
			this.setDate(this.deleteDodAndComma(res2.get(lenght - 2)));
			res2.remove(lenght - 2);
			this.setMail(this.getAllMail(res2));

			return true;
		} else {
			try {
				this.writeLine(line, this.getDirOut().concat(File.separator).concat("ErrorsLog_" + Text.DATE_FORMAT.format(this.getFiledate())).concat(".txt"));
				int errores = this.getErrores();
				this.setErrores(errores + 1);
			} catch (IOException e) {
				MessageUtils.error(e.getMessage());
			}
		}
		return false;
	}

	private void verityCodeError(String line) {
		List<String> res = new ArrayList(Arrays.asList(line.split(Text.ERROR_CODE)));
		this.setCodeError(this.deleteDodAndComma(res.get(1)));
	}

	private void verifyDescError(String line) {
		List<String> res = new ArrayList(Arrays.asList(line.split(Text.ERROR_DESCRIPTION)));
		this.setDescriptionError(this.deleteDodAndComma(res.get(1)));
	}
}
