package export;

import java.io.OutputStream;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import utils.CommonUtils;

public class DocumentPrinter
{
	public static void printPdf(HttpServletResponse response, String fileName, byte[] stream) throws Exception{
		print(response, stream, fileName, ".pdf", "application/pdf");
	}
	
	public static void printCsv(HttpServletResponse response, String fileName, byte[] stream) throws Exception{
		print(response, stream, fileName, ".csv", "text/csv");
	}
	
	public static void printDocumentum(HttpServletResponse response, byte[] stream, String fileName, String mimeType) throws Exception{
		print(response, stream, fileName, null, mimeType);
	}
	
	
	private static void print(HttpServletResponse response, byte[] stream, String fileName, String estensione, String type) throws Exception
	{
		if (estensione != null) {
			//Il nome del file è seguito dalla data del download e dal tempo trascorso in millisecondi dall'anno zero dell'informatica (01/01/1970)
			fileName += "_" + CommonUtils.dateToString(new Date()) + "_" + System.currentTimeMillis() + estensione;
		}
		
		printResponse(response,fileName, type);
		OutputStream out = response.getOutputStream();
		out.write(stream);
		out.flush();
		out.close();
		FacesContext.getCurrentInstance().responseComplete();
	}
	
	private static void printResponse(HttpServletResponse response, String fileName, String type)
	{
		response.setHeader("Cache-Control", "max-age=30");
		response.setHeader("Pragma", "no-cache");
		response.setContentType(type);
		response.setHeader("Content-disposition","attachment; filename=" + fileName);
	}
}
