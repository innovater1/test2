package testDBcon;

import com.example.model.News;
import com.example.model.NewsDAO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.util.List;

@Log4j2
public class TestDBConnection {
    @Test
    public void testDBConnection () throws Exception {
        NewsDAO newsDAO = new NewsDAO();
        Connection connection = newsDAO.open();
        log.info(connection);
    }
    @Test
    public void testInsertNews () throws Exception {
        NewsDAO newsDAO = new NewsDAO();
        News news = new News();
        news.setTitle("제목");
        news.setImg("sample.jpg");
        news.setContent("내용");

        newsDAO.insertNews(news);

        for (int i = 1; i <= 10; i++) {
            News news1 = new News();
            news1.setTitle("제목" + i);
            news1.setImg("sample" + i + ".jpg");
            news1.setContent("내용" + i);

            newsDAO.insertNews(news1);
        }
    }
    @Test
    public void testSelectAll() throws Exception {
        NewsDAO newsDAO = new NewsDAO();
        List<News> newsList = newsDAO.selectAll();

        // 1) 람다와 스트링 이용해서 출력
        newsList.forEach(news -> log.info(news));

        // 2) forEach 사용
        for (News news : newsList) {
            log.info(news);
        }
    }

    @Test
    public void testSelectOne() throws Exception {
        NewsDAO newsDAO = new NewsDAO();
        int aid = 12;
        News news = newsDAO.selectOne(aid);
        log.info(news);
    }

    @Test
    public void testDeleteNews() throws Exception {
        NewsDAO newsDAO = new NewsDAO();
        int aid = 12;
        log.info(newsDAO);
        newsDAO.deleteNews(aid);
        log.info(newsDAO);
    }
}
