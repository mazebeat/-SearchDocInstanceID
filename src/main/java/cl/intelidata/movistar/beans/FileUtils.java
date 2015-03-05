package cl.intelidata.movistar.beans;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

	public FileUtils() {
	}


	public static Boolean writeFile(String file, String whereOut, List<String> list) throws IOException {
		File t = new File(file);
		File f = new File(whereOut.concat(File.separator).concat(t.getName()));
		FileWriter fw = new FileWriter(f.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);

		try {
			if (!f.exists()) {
				f.createNewFile();
			}

			for (String l : list) {
				bw.append(l);
				bw.newLine();
			}

			return true;
		} catch (Exception e) {
			MessageUtils.error(e.getMessage());
		} finally {
			bw.close();
		}

		return false;

	}

	public static List<String> readDirectory(String directory, String findByExt) {
		List<String> listFiles = new ArrayList<String>();
		String filter = findByExt.length() > 0 ? findByExt : "";
		try {
			File dir = new File(directory);
			File tempList[] = dir.listFiles();
			String nameFile;
			if (tempList == null) {
				MessageUtils.error("No se encontraron archivos en el directorio.");
			} else {
				if (filter != "") {
					for (File path : tempList) {
						nameFile = path.getAbsolutePath();
						if (nameFile.contains(filter)) {
							listFiles.add(nameFile);
						}
					}
				} else {
					for (File path : tempList) {
						nameFile = path.getAbsolutePath();
						listFiles.add(nameFile);
					}
				}
			}
		} catch (Exception e) {
			MessageUtils.error(e.getMessage());
			System.exit(1);
		}
		return listFiles;
	}

	public static String fullReadFile(String filename) {
		String content = null;
		File file = new File(filename);

		try {
			FileReader r = new FileReader(file);
			try {
				char[] chars = new char[(int)file.length()];
				r.read(chars);
				content = new String(chars);
			} catch (IOException e) {
				MessageUtils.error(e.getMessage());
			} finally {
				r.close();
			}
		} catch (Exception e) {
			MessageUtils.error(e.getMessage());
		}

		return content;
	}
}