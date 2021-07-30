package controller;

import entity.Article;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import dal.ArticleDAO;

/**
 *
 * @author Riel
 */
public class SearchController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            final int ARTICLE_PAGE = 2;
            HttpSession session = request.getSession();

            int pageCurrent = Integer.parseInt(request.getParameter("page"));
            String keyword = (String) session.getAttribute("keyword");

            // get most and five recent article
            ArticleDAO articles = new ArticleDAO();
            Article mostRecentArticle = articles.getRecentArticle(1).get(0);
            request.setAttribute("mostRecentArticle", mostRecentArticle);
            ArrayList<Article> fiveRecentAticle = articles.getRecentArticle(5);
            request.setAttribute("fiveRecentAticle", fiveRecentAticle);

            // get list article found
            ArrayList<Article> listSearch = articles.getListAticleSearch(ARTICLE_PAGE, pageCurrent, keyword);
            request.setAttribute("listSearch", listSearch);

            // highlight text after search
            String string = "";
            String title = "";
            String pre = "<b style=\"background-color: yellow;\">";
            String post = "</b>";
            if (listSearch != null) {
                for (Article article : listSearch) {
                    string = article.getTitle();
                    for (int i = 0; i < string.length() - keyword.length() + 1; i++) {
                        if (string.substring(i, i + keyword.length()).equalsIgnoreCase(keyword)) {
                            title += pre + string.substring(i, i + keyword.length()) + post;
                            i += keyword.length() - 1;
                        } else {
                            title += string.charAt(i);
                        }
                    }
                    if (!string.substring(string.length() - keyword.length()).equalsIgnoreCase(keyword)) {
                        title += string.substring(string.length() - keyword.length() + 1);
                    }
                    article.setTitle(title);
                }
            }

            // get number page to paging
            int numberPage = articles.getNumberPage(ARTICLE_PAGE, keyword);
            request.setAttribute("numberPage", numberPage);

            // get page current
            request.setAttribute("pageCurrent", pageCurrent);

            //get number articles
            int numberArticle = articles.getNumberArticle(ARTICLE_PAGE, keyword);
            String message = "Keyword '" + keyword + "' has " + numberArticle + " results!";
            request.setAttribute("message", message);
            request.setAttribute("keyw", keyword);

            request.getRequestDispatcher("/view/search.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("error", "Sorry! Error occurred");
            request.getRequestDispatcher("/view/search.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String keyword = request.getParameter("keyword");

            // check keyword empty
            if (keyword == null || keyword.isEmpty()) {
                // refresh page 
                String servletPrev = request.getHeader("referer");
                String nameServletPrev = servletPrev.substring(servletPrev.lastIndexOf("/") + 1);
                response.sendRedirect(nameServletPrev);
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("keyword", keyword);
                response.sendRedirect("Search?page=1");
            }
        } catch (Exception e) {
            request.setAttribute("error", "Sorry! Error occurred");
            request.getRequestDispatcher("/view/search.jsp").forward(request, response);
        }
    }
}
