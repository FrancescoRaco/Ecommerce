package export;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dto.OrdineDTO;
import utils.CommonUtils;

public class StampaOrdine {
	
	/**
	 * Logger
	 */
	private final static Logger logger = LogManager.getLogger(StampaOrdine.class);
	protected PdfContentByte cbr;
	
	/**
	 * Font
	 */
	private static Font fontTitoli = new Font(FontFamily.HELVETICA, 14, Font.BOLD);
	private static Font fontDatiNormal = new Font(FontFamily.HELVETICA, 10, Font.NORMAL);
  	
	private Map<String, Paragraph> createMappaContenuti(OrdineDTO ordineDTO) {
  		
		Map<String, Paragraph> mappaParagrafi = new TreeMap<String, Paragraph>();
  		
  		mappaParagrafi.put("riga1P1", new Paragraph("Identificativo prodotto", fontDatiNormal));
  		mappaParagrafi.put("riga1P2", new Paragraph(ordineDTO.getIdProdotto() == null ? "---" : ""+ordineDTO.getIdProdotto(), fontDatiNormal));
  		mappaParagrafi.put("riga1P3", new Paragraph("        Acquirente", fontDatiNormal));
  		mappaParagrafi.put("riga1P4", new Paragraph(ordineDTO.getCfAcquirente() == null ? "---" : ordineDTO.getCfAcquirente(), fontDatiNormal));
  		
  		mappaParagrafi.put("riga2P1", new Paragraph("Offerta", fontDatiNormal));
  		mappaParagrafi.put("riga2P2", new Paragraph(ordineDTO.getOfferta() == null ? "---" : ""+ordineDTO.getOfferta(), fontDatiNormal));
  		mappaParagrafi.put("riga2P3", new Paragraph("        Quantità", fontDatiNormal));
  		mappaParagrafi.put("riga2P4", new Paragraph(ordineDTO.getQuantita() == null ? "---" : ""+ordineDTO.getQuantita(), fontDatiNormal));
  		
  		mappaParagrafi.put("riga3P1", new Paragraph("Progressivo", fontDatiNormal));
  		mappaParagrafi.put("riga3P2", new Paragraph(ordineDTO.getProgressivo() == null ? "---" : ""+ordineDTO.getProgressivo(), fontDatiNormal));
  		mappaParagrafi.put("riga3P3", new Paragraph("        Stato", fontDatiNormal));
  		mappaParagrafi.put("riga3P4", new Paragraph(ordineDTO.getFlagAccettazione() == null ? "Non valido" : ordineDTO.getFlagAccettazione() == 0 ? "In lavorazione" : ordineDTO.getFlagAccettazione() == 1 ? "Accettato" : "Rifiutato", fontDatiNormal));
  		
  		mappaParagrafi.put("riga4P1", new Paragraph("Note acquirente", fontDatiNormal));
  		mappaParagrafi.put("riga4P2", new Paragraph(ordineDTO.getNoteAcquirente() == null ? "---" : ordineDTO.getNoteAcquirente(), fontDatiNormal));
  		
  		
  		mappaParagrafi.put("riga5P1", new Paragraph("Data ordine", fontDatiNormal));
  		String dataOrdine = null;
  		if (ordineDTO.getDataOrdine() != null) {
  			dataOrdine = CommonUtils.dateToString(ordineDTO.getDataOrdine());
  		}
  		mappaParagrafi.put("riga5P2", new Paragraph(dataOrdine == null ? "---" : dataOrdine, fontDatiNormal));
        
        return mappaParagrafi;
  	}
  	
	public List<PdfPTable> createListaContenuti(Map<String, Paragraph> paragrafi) {
		
		ArrayList<PdfPTable> tableList = new ArrayList<PdfPTable>();
		
		try {
			PdfPTable row1 = new PdfPTable(4);
            row1.setWidths(new float[] {0.65f, 1, 0.5f, 1});
            
            PdfPTable row2 = new PdfPTable(4);
            row2.setWidths(new float[] {0.2f, 1, 0.350f, 1});
            
            PdfPTable row3 = new PdfPTable(4);
            row3.setWidths(new float[] {0.35f, 1, 0.3f, 1});
            
            PdfPTable row4 = new PdfPTable(2);
            row4.setWidths(new float[] {0.19f, 1});
            
            PdfPTable row5 = new PdfPTable(2);
            row5.setWidths(new float[] {0.14f, 1,});
            							 
            PdfPCell cell1 = new PdfPCell();
            cell1.addElement(paragrafi.get("riga1P1"));
            cell1.setBorder(PdfPCell.NO_BORDER);
            
            PdfPCell cell2 = new PdfPCell();
            cell2.addElement(paragrafi.get("riga1P2"));
            cell2.setBorder(PdfPCell.BOTTOM);
            
            PdfPCell cell3 = new PdfPCell();
            cell3.addElement(paragrafi.get("riga1P3"));
            cell3.setBorder(PdfPCell.NO_BORDER);
            
            PdfPCell cell4 = new PdfPCell();
            cell4.addElement(paragrafi.get("riga1P4"));
            cell4.setBorder(PdfPCell.BOTTOM);
            
            PdfPCell cell5 = new PdfPCell();
            cell5.addElement(paragrafi.get("riga2P1"));
            cell5.setBorder(PdfPCell.NO_BORDER);
            
            PdfPCell cell6 = new PdfPCell();
            cell6.addElement(paragrafi.get("riga2P2"));
            cell6.setBorder(PdfPCell.BOTTOM);
            
            PdfPCell cell7 = new PdfPCell();
            cell7.addElement(paragrafi.get("riga2P3"));
            cell7.setBorder(PdfPCell.NO_BORDER);
            
            PdfPCell cell8 = new PdfPCell();
            cell8.addElement(paragrafi.get("riga2P4"));
            cell8.setBorder(PdfPCell.BOTTOM);
            
            PdfPCell cell9 = new PdfPCell();
            cell9.addElement(paragrafi.get("riga3P1"));
            cell9.setBorder(PdfPCell.NO_BORDER);
            
            PdfPCell cell10 = new PdfPCell();
            cell10.addElement(paragrafi.get("riga3P2"));
            cell10.setBorder(PdfPCell.BOTTOM);
            
            PdfPCell cell11 = new PdfPCell();
            cell11.addElement(paragrafi.get("riga3P3"));
            cell11.setBorder(PdfPCell.NO_BORDER);
            
            PdfPCell cell12 = new PdfPCell();
            cell12.addElement(paragrafi.get("riga3P4"));
            cell12.setBorder(PdfPCell.BOTTOM);
            
            PdfPCell cell13 = new PdfPCell();
            cell13.addElement(paragrafi.get("riga4P1"));
            cell13.setBorder(PdfPCell.NO_BORDER);
            
            PdfPCell cell14 = new PdfPCell();
            cell14.addElement(paragrafi.get("riga4P2"));
            cell14.setBorder(PdfPCell.BOTTOM);
            
            PdfPCell cell15 = new PdfPCell();
            cell15.addElement(paragrafi.get("riga5P1"));
            cell15.setBorder(PdfPCell.NO_BORDER);
            
            PdfPCell cell16 = new PdfPCell();
            cell16.addElement(paragrafi.get("riga5P2"));
            cell16.setBorder(PdfPCell.BOTTOM);
            
            row1.setWidthPercentage(100);
            row1.addCell(cell1);
            row1.addCell(cell2);
            row1.addCell(cell3);
            row1.addCell(cell4);
            
            row2.setWidthPercentage(100);
            row2.addCell(cell5);
            row2.addCell(cell6);
            row2.addCell(cell7);
            row2.addCell(cell8);
            
            row3.setWidthPercentage(100);
            row3.addCell(cell9);
            row3.addCell(cell10);
            row3.addCell(cell11);
            row3.addCell(cell12);
            
            row4.setWidthPercentage(100);
            row4.addCell(cell13);
            row4.addCell(cell14);
            
            row5.setWidthPercentage(100);
            row5.addCell(cell15);
            row5.addCell(cell16);
            
            tableList.addAll(Arrays.asList(row1, row2, row3, row4, row5));
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
  		}
  		
  		return tableList;
  	}
	
  	public ByteArrayOutputStream produciPdf(OrdineDTO ordineDTO) {
		
  		ByteArrayOutputStream baosPDF =null;
		
		//Document document = new Document(PageSize.A4, 35, 35, 18, 35);
		Document document = new Document(PageSize.A4, 50, 50, 20, 29);
//		logger.debug("Stato: GENERA PDF");
		
		baosPDF = new ByteArrayOutputStream();
		try {
				PdfWriter writer = PdfWriter.getInstance(document, baosPDF);
				document.open();
				
				document.addTitle("Ricevuta PDF ordine");
				document.addAuthor("Ecommerce");
				document.addCreator("Ecommerce");
				document.addSubject("" + ordineDTO.getIdProdotto() + " " + ordineDTO.getProgressivo());
				
				 // Linea vuota
	            Paragraph paragraph = new Paragraph();
	            paragraph.add(new Paragraph(" "));
	                        
	            Paragraph intestazione = new Paragraph("Stampa ricevuta dell'ordine: " + ordineDTO.getIdProdotto() + " " + ordineDTO.getProgressivo(), fontTitoli);
				
				Map<String, Paragraph> paragrafiBody = createMappaContenuti(ordineDTO);
	            
	            List<PdfPTable> tabellaBody = createListaContenuti(paragrafiBody);
	            
	            /* Aggiungi al document gli elementi*/
	            intestazione.setAlignment(Element.ALIGN_CENTER);
		        intestazione.setSpacingAfter(8);
		        document.add(intestazione);
		          
		        //row1
		        PdfPTable row1 = tabellaBody.get(0);
		        row1.setSpacingAfter(4);
		        document.add(row1);
		          
		        //row2
		        PdfPTable row2 = tabellaBody.get(1);
		        row2.setSpacingAfter(4);
		        document.add(row2);
		          
		        //row3
		        PdfPTable row3 = tabellaBody.get(2);
		        row3.setSpacingAfter(4);
		        document.add(row3);
		          
		        //row4
		        PdfPTable row4 = tabellaBody.get(3);
		        row4.setSpacingAfter(4);
		        document.add(row4);
		          
		        //row5
		        PdfPTable row5 = tabellaBody.get(4);
		        row5.setSpacingAfter(4);
		        document.add(row5);
		          
		       document.add(Chunk.NEWLINE);
	            
	           document.close();
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} 

		return baosPDF;
	}
	
//	public static void byteArrayToPdf(byte[] b) {          
//		OutputStream out = null;
//	    try {       
//	        out = new FileOutputStream("C:/Users/ACER/OneDrive/Desktop/stampaOrdine.pdf");
//	        out.write(b);
//	        out.close();
//	    } catch (Exception e) {
//	    	logger.error(e.getMessage(), e);
//	    }
//	}
//	
//	public static OrdineDTO getOrdineTest() {
//		OrdineDTO ordine = new OrdineDTO();
//	
//		try {
//			//Dati di prova
//			ordine.setIdProdotto(1);
//			ordine.setProgressivo(1);
//			ordine.setCfAcquirente("RCAFDR94H26H501M");
//			ordine.setQuantita(3);
//			ordine.setFlagAccettazione(0);
//			ordine.setNoteAcquirente("Attendo informazioni sulla spedizione");
//			ordine.setDataOrdine(CommonUtils.stringToDate("30/06/2021"));
//			ordine.setOfferta(100);
//		} catch(Exception e) {
//			logger.error(e.getMessage(), e);	
//		}
//		
//		return ordine;
//	}
//
//	public static void main(String[] args) {
//		OrdineDTO ordine = getOrdineTest();
//		ByteArrayOutputStream baos = new StampaOrdine().produciPdf(ordine);
//		byteArrayToPdf(baos.toByteArray());
//	}

}
