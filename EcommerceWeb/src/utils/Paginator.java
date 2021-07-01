package utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Paginator<T> implements Serializable {
	

	private static final long serialVersionUID = -7872595859385530279L;
	
	private List<T> resultset;
	private int currentPageNumber = 1;
	private int pageSize = 1;
	private int windowSize = 5;
	private int result;

	public List<T> getResultset()
	{
		return getPage();
	}

	public void setResultset(List<T> resultset)
	{
		this.resultset = resultset;
	}
	
	public void clear()
	{
		setResultset(new ArrayList<T>());
	}

	public List<T> getPage()
	{
		if (resultset == null || resultset.isEmpty())
		{
			return resultset;
		}
		int fromIndex = ((currentPageNumber - 1) * pageSize);
		int toIndex = (currentPageNumber * pageSize);
		return resultset.subList((fromIndex < 0) ? 0 : fromIndex, (toIndex > resultset.size()) ? resultset.size() : toIndex);
	}

	public long getCurrentPageNumber()
	{
		return currentPageNumber;
	}

	public void setCurrentPageNumber(int currentPageNumber)
	{
		this.currentPageNumber = currentPageNumber;
	}

	public int getTotalPages()
	{
		this.result = (pageSize > 0 && getTotalItems() > 0) ? ((int) getTotalItems() / pageSize) : 0;
		if ((getTotalItems() % pageSize) > 0)
		{
			this.result += 1;
		}
		return this.result;
	}

	public int getTotalItems()
	{
		return (resultset != null && !resultset.isEmpty()) ? resultset.size() : 0;
	}

	public void goFirstPage()
	{
		currentPageNumber = 1;
	}

	public void goToPage(long pageNumber)
	{
		currentPageNumber = (int) pageNumber;
	}
	
	public void goToPage(int num)
	{
		currentPageNumber = num;
	}

	public void goLastPage()
	{
		currentPageNumber = getTotalPages();
	}

	public void nextPage()
	{
		if (!isLastPage())
		{
			currentPageNumber += 1;
		}
	}

	public void prevPage()
	{
		if (!isFirstPage())
		{
			currentPageNumber -= 1;
		}
	}

	public List<Integer> getPageWindow()
	{
		List<Integer> pages = new ArrayList<Integer>();

		int half = windowSize / 2;
		
		if (currentPageNumber > 1)
		{
			// Ho pagine a sinistra della pagina corrente
			// Inizializzo un puntatore alla pagina precedente
			int pointer = currentPageNumber - 1;
			// Aggiungo un numero di pagine a sinistra non superiore ad half
			while (pointer > 0 && (currentPageNumber - pointer <= half))
			{
				// Aggiungi in testa alla lista per mantenere il corretto
				// ordinamento
				pages.add(0, pointer--);
			}
		}

		//Aggiungo la pagina corrente
		pages.add(currentPageNumber);
		
		if (currentPageNumber < result)
		{
			// Ho pagine a destra della pagina corrente
			// Inizializzo un puntatore alla pagina successiva
			int pointer = currentPageNumber + 1;
			// Aggiungo un numero di pagine a destra non superiore ad half
			while (pointer <= result && (pointer - currentPageNumber <= half))
			{
				pages.add(pointer++);
			}
		}

		return pages;
	}
	
	public boolean isFirstPage()
	{
		return currentPageNumber <= 1;
	}

	public boolean isLastPage()
	{
		return currentPageNumber >= getTotalPages();
	}

	public void setFirstPage(boolean firstPage)
	{ 
		/* this.firstPage = firstPage; */
	}

	public void setLastPage(boolean lastPage)
	{
		/* this.lastPage = lastPage; */
	}

	public void setTotalPages(int totalPages)
	{
		/* this.totalPages = totalPages; */
	}

	public void setTotalItems(int totalItems)
	{
		/* this.totalItems = totalItems; */
	}

	public void setPage(List<?> page)
	{
		/* this.page=page; */
	}

	/**
	 * Record totali
	 * 
	 * @return restituisce il totale dei record
	 */
	public List<T> getTotalElements()
	{
		return resultset;
	}


	/**
	 * Ritorna il numero di pagine mostrate dal paginatore default 5.
	 * 
	 * @return numero delle pagine mostrate dal paginatore
	 */
	public int getWindowSize()
	{
		return windowSize;
	}

	/**
	 * Set del numero di pagine mostrate dal paginatore default 5.
	 * Valori minori o uguali a zero non sono considerati.
	 * 
	 * @param windowSize
	 *            numero di pagine mostrate dal paginatore
	 */
	public void setWindowSize(int windowSize)
	{
		this.windowSize = windowSize;
		if(windowSize<=0)
		{
			this.windowSize = 5;
		}
	}
	
	/**
	 * Ritorna la dimensione della pagina
	 * 
	 * @return dimensione della pagina
	 */
	public long getPageSize()
	{
		return pageSize;
	}
	
	/**
	 * Setta la dimensione della pagina. 
	 * Valori minori o uguali a zero non sono
	 * considerati.
	 * 
	 * @param pageSize
	 *            dimensione della pagina
	 */
	public void setPageSize(int pageSize)
	{
		currentPageNumber = 1;
		this.pageSize = pageSize;
		if (pageSize <= 0)
		{
			this.pageSize = 5;
		}
	}

}
