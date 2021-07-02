package export;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import dto.OrdineDTO;


public class DocumentExporter {
	
	private final static Logger logger = LogManager.getLogger(DocumentExporter.class);
	
	public static void exportPdfOrdine(OrdineDTO ordineDTO) {
		HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();  
	    try{	
	    	DocumentPrinter.printPdf(response, "ordine", new StampaOrdine().produciPdf(ordineDTO).toByteArray());
	    } catch(Exception e) {
	    	logger.error(e.getMessage(), e);
	    }
	}

}
