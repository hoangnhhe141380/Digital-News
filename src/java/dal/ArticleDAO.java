package dal;

import entity.Article;
import db.DBContext;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Riel
 */
public class ArticleDAO {

    /**
     * Get top number article
     *
     * @param numberArticle
     * @return list top number article
     */
    public ArrayList<Article> getRecentArticle(int numberArticle) {
        DBContext db = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT TOP (?) * "
                + "FROM Article \n"
                + "ORDER BY Date DESC";

        ArrayList<Article> listArticle = new ArrayList<>();
        try {
            db = new DBContext();
            con = db.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, numberArticle);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                String title = rs.getString(2);
                String image = rs.getString(3);
                String content = rs.getString(4);
                Date date = rs.getDate(5);
                String author = rs.getString(6);
                Article article = new Article(id, title, image, content, date, author);
                listArticle.add(article);
            }
            return listArticle;

        } catch (Exception ex) {
            Logger.getLogger(ArticleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Get article by id
     *
     * @param id
     * @return article if existed
     */
    public Article getArticleById(int id) {
        DBContext db = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT title, image, content, date, author FROM Article WHERE id = ?";

        try {
            db = new DBContext();
            con = db.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                String title = rs.getString(1);
                String image = rs.getString(2);
                String content = rs.getString(3);
                Date date = rs.getDate(4);
                String author = rs.getString(5);
                return new Article(id, title, image, content, date, author);
            }

        } catch (Exception ex) {
            Logger.getLogger(ArticleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Get list article by keyword and page number
     *
     * @param numberArticleInPage
     * @param pageCurrent
     * @param keyword
     * @return list article by page and keyword
     */
    public ArrayList<Article> getListAticleSearch(int numberArticleInPage, int pageCurrent, String keyword) {
        DBContext db = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Article> listArticle = new ArrayList<>();

        String sql = "SELECT * FROM (\n"
                + "SELECT ROW_NUMBER()\n"
                + "OVER(ORDER BY id) as Number,\n"
                + "* FROM Article \n"
                + "WHERE title LIKE ? \n"
                + ") as DataSearch where Number between ? and ?";
        try {
            db = new DBContext();

            int articleFrom = pageCurrent * numberArticleInPage - 1;
            int articleTo = articleFrom + numberArticleInPage - 1;

            con = db.getConnection();
            ps = con.prepareStatement(sql);
            keyword = "%" + keyword + "%";
            ps.setString(1, keyword);
            ps.setInt(2, articleFrom);
            ps.setInt(3, articleTo);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(2);
                String title = rs.getString(3);
                String image = rs.getString(4);
                String content = rs.getString(5);
                Date date = rs.getDate(6);
                String author = rs.getString(7);
                Article article = new Article(id, title, image, content, date, author);
                listArticle.add(article);
            }
            return listArticle;
        } catch (Exception ex) {
            Logger.getLogger(ArticleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Get number page search by keyword
     *
     * @param numberArticleInPage
     * @param keyword
     * @return number page
     */
    public int getNumberPage(int numberArticleInPage, String keyword) throws Exception {
        DBContext db = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT COUNT(id) FROM Article \n"
                + "WHERE title LIKE ?";
        try {
            db = new DBContext();
            con = db.getConnection();
            ps = con.prepareStatement(sql);
            keyword = "%" + keyword + "%";
            ps.setString(1, keyword);
            rs = ps.executeQuery();

            while (rs.next()) {
                int numberArticle = rs.getInt(1);
                return (numberArticle + (numberArticle % numberArticleInPage)) / numberArticleInPage;
            }
        } catch (Exception ex) {
            Logger.getLogger(ArticleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    /**
     * Get total number article search by keyword
     *
     * @param numberArticleInPage
     * @param keyword
     * @return total number article search by keyword
     */
    public int getNumberArticle(int numberArticleInPage, String keyword) {
        DBContext db = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT COUNT(id) FROM Article \n"
                + "WHERE title LIKE ?";
        try {
            db = new DBContext();
            con = db.getConnection();
            ps = con.prepareStatement(sql);
            keyword = "%" + keyword + "%";
            ps.setString(1, keyword);
            rs = ps.executeQuery();

            while (rs.next()) {
                int numberArticle = rs.getInt(1);
                return numberArticle;
            }
        } catch (Exception ex) {
            Logger.getLogger(ArticleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
