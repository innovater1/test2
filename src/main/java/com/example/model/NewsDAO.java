package com.example.model;

import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class NewsDAO {
    final String jdbcDriver = "org.mariadb.jdbc.Driver";
    final String jdbcUrl = "jdbc:mariadb://localhost:3308/api_news";
    final String jdbcUser = "root";
    final String jdbcPassword = "9559";

    public Connection open() {
        /* 디비 연결을 하는 메서드.
        각각의 메서드마다 연결을 만들고 해제하는 구조
         */
        Connection connection = null;
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return connection;
    }
    public void insertNews(News news) throws SQLException {
        // 뉴스를 추가하는 메서드
        @Cleanup Connection connection = open();
        String sql = "insert into news (title, img, date, content) values (?, ?, now(), ?)";
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, news.getTitle());
        preparedStatement.setString(2, news.getImg());
        preparedStatement.setString(3, news.getContent());
        preparedStatement.executeUpdate();
    }

    public List<News> selectAll() throws SQLException {
        // 뉴스 기사 목록 전체를 가져오기 위한 메서드
        @Cleanup Connection connection = open();
        List<News> newsList = new ArrayList<>();

        String sql = "select * from news";
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            News news = new News();
            news.setAid(resultSet.getInt("aid"));
            news.setTitle(resultSet.getString("title"));
            news.setDATE(resultSet.getString("date"));
            newsList.add(news);
        }
        return newsList;
    }
    public News selectOne(int aid) throws SQLException {
        // 뉴스 목록에서 뉴스를 선택했을 때 특정 뉴스 기사의 세부 내용을 보여주기 위한 메서드
        log.info("selectOne(int aid)...");
        @Cleanup Connection connection = open();
        News news = null;

        String sql = "select * from news where aid = ?";
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, aid);
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            news = new News();
            news.setAid(resultSet.getInt("aid"));
            news.setTitle(resultSet.getString("title"));
            news.setImg(resultSet.getString("img"));
            news.setDATE(resultSet.getString("date"));
            news.setContent(resultSet.getString("content"));
        }
        return news;
    }
    public void deleteNews(int aid) throws SQLException {
        // 뉴스 삭제
        @Cleanup Connection connection = open();
        String sql = "delete from news where aid = ?";
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, aid);
        if (preparedStatement.executeUpdate() == 0) { // 삭제된 뉴스 기사가 없는 경우
            throw new SQLException("DB에러");
        }
    }
}
