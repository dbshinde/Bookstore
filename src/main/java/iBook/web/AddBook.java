package iBook.web;

import iBook.dao.CategoryDao;
import iBook.dao.factory.DaoFactory;
import iBook.domain.Authors2Books;
import iBook.domain.Book;
import iBook.domain.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Bean respoonsible for Book add.
 */
public class AddBook extends Form {
    /**
     * Parameter specifying title.
     */
    public static final String PARAM_TITLE = "title";
    /**
     * Parameter specifying author.
     */
    public static final String PARAM_AUTHOR = "author";
    /**
     * Parameter specifying category.
     */
    public static final String PARAM_CATEGORY = "category";
    /**
     * Parameter specifying bestsellers.
     */
    public static final String PARAM_BESTSELLER = "bestSeller";
    
    
    public static final String PARAM_PUBLISH_DATE = "publishDate";
    
    
    public static final String PARAM_PRICE = "price";
    
    
    public static final String PARAM_PAGES = "pages";
    
    
    public static final String PARAM_DESCRIPTION = "description";
    /**
     * Parameter specifying that no category is selected.
     */
    public static final String NO_CATEGORY = "no_cat";

    public static final String ERROR_MSGS = "errorMsgs";
    
    private List<Book> searchedBookList = null;

    @Override
    public void submit() throws Exception 
    {
    	String title = getParameter(PARAM_TITLE);
    	String author = getParameter(PARAM_AUTHOR);
    	String category = getParameter(PARAM_CATEGORY);
    	String bestSeller = getParameter(PARAM_BESTSELLER);
    	String publishDate = getParameter(PARAM_PUBLISH_DATE);
    	String pages = getParameter(PARAM_PAGES);
    	String price = getParameter(PARAM_PRICE);
    	String description = getParameter(PARAM_DESCRIPTION);

    	List<String> errorMsgs = validateForm();
    	if(errorMsgs.isEmpty())
    	{
    		Book book = new Book();
    		DaoFactory.getInstance().getBookDao().save(book);
    		response.sendRedirect(ADMIN_DASHBOARD_URL);

    	}
    	else {
    		request.setAttribute("errorMsgs", errorMsgs);
    		request.getRequestDispatcher(ADD_BOOK_URL).forward(request, response);
    	}

    }

    /**
     * Returns all categories.
     * @return      all categories list.
     */
    public List<Category> getCategories() {
        List<Category> categories = null;
        CategoryDao category = DaoFactory.getInstance().getCategoryDao();
        if(category != null) {
            categories = category.getAllCategories();
        }

        return categories;
    }

    public List<String> validateForm() 
    {
    	List<String> errorMsgs = new ArrayList<String>();
    	Map<String , Object> requestData = getParameters();
    	for( String key: requestData.keySet())
    	{
    		String[] valueArr = (String[]) requestData.get(key);
    		String value = valueArr[0];
    		if( value == null || "".equals(value))
    		{
    			errorMsgs.add("The "+ key +" should not be empty!");
    		}
    		if( PARAM_CATEGORY.equals(key) && (value == null || NO_CATEGORY.equals(value)))
    		{
    			errorMsgs.add("Please select category");
    		}
    	}
		return errorMsgs;
    }
}
