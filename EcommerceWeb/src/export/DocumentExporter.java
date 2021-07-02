package export;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import dto.OrdineDTO;


public class DocumentExporter
{
	public static void exportPdfOrdine(OrdineDTO ordineDTO){
		HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();  
	    try{	
	    	DocumentPrinter.printPdf(response, "ordine", new StampaOrdine().produciPdf(ordineDTO).toByteArray());
	    }catch(Exception e){
	    	
	    }
	}

}
