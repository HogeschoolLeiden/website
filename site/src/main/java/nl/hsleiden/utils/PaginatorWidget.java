package nl.hsleiden.utils;

/**
 * @author Ebrahim Aharpour
 *
 */
public class PaginatorWidget {
	private static final int DEFAULT_NUMBER_OF_PAGES_DISPLAY = 9;
	private final int totalRows;
	private final int rowsPerPage;
	private final int page;
	private final int numberOfPagesDisplay;

	private int firstShownPage;
	private int lastShownPage;
	private final int numberOfPages;

	/**
	 * @param totalRows
	 * @param page
	 */
	public PaginatorWidget(int totalRows, int page, int rowsPerPage) {
		this(totalRows, page, rowsPerPage, DEFAULT_NUMBER_OF_PAGES_DISPLAY);
	}

	public PaginatorWidget(int totalRows, int page, int rowsPerPage, int numberOfPagesDisplay) {
		rowsPerPage = Math.max(1, rowsPerPage);
		this.totalRows = totalRows;
		this.rowsPerPage = rowsPerPage;
		this.numberOfPages = calcaulateNumberOfPages(totalRows, rowsPerPage);
		this.page = Math.max(Math.min(page, numberOfPages), 1);
		this.numberOfPagesDisplay = numberOfPagesDisplay;
		init();
	}

	public void init() {
		firstShownPage = page;
		for (; firstShownPage > 1 && firstShownPage > (this.page - ((numberOfPagesDisplay - 1) / 2)); firstShownPage--) {
		}

		lastShownPage = Math.min(firstShownPage + numberOfPagesDisplay - 1, numberOfPages);
		firstShownPage = Math.max(Math.min(firstShownPage, lastShownPage - numberOfPagesDisplay) + 1, 1);
	}

	private int calcaulateNumberOfPages(int totalRows, int rowsPerPage) {
		return totalRows / rowsPerPage + (totalRows % rowsPerPage > 0 ? 1 : 0);
	}

	public int getFirstDisplayedItemIndex() {
		return (page - 1) * rowsPerPage + (totalRows > 0 ? 1 : 0);
	}

	public int getLastDisplayedItemIndex() {
		return Math.min(totalRows, page * rowsPerPage);
	}

	public int getRowsPerPage() {
		return rowsPerPage;
	}

	public int getPage() {
		return page;
	}

	public int getFirstShownPage() {
		return firstShownPage;
	}

	public int getLastShownPage() {
		return lastShownPage;
	}

	public int getNumberOfPages() {
		return numberOfPages;
	}

	public int getTotalRows() {
		return totalRows;
	}

}