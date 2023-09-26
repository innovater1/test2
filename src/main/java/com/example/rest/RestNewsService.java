package com.example.rest;

import com.example.model.News;
import com.example.model.NewsDAO;
import org.checkerframework.checker.units.qual.N;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

@Path("/news")
public class RestNewsService {
    NewsDAO dao;

    public RestNewsService() {
        dao = new NewsDAO();
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON) // 클라이언트 요청에 포함된 미디어타입을 지정. JSON을 사용.
    public String addNews(News news) {
        // 뉴스 등록
        try {
            dao.insertNews(news);
            /* @Consumes 설정에 따라 HTTP Body에 포함된 JSON 문자열이 자동으로 News로 변환.
            이를 위해서 JSON 문자열의 키와 News 객체의 멤버변수명이 동일해야 함.
             */
        } catch (Exception e) {
            e.printStackTrace();
            return "News API : 뉴스 등록 실패";
        }
        return "News API : 뉴스 등록됨!!";
    }
    @GET
    @Path("{aid}") // api/news/100의 형태로 요청을 하면 100이 aid의 값으로 자동 처리됨.
    @Produces(MediaType.APPLICATION_JSON)
    public News getNews(@PathParam("aid") int aid) {
        News news = null;
        try {
            news = dao.selectOne(aid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return news;
        // @Produces 설정으로 News 객체가 JSON 문자열로 출력.
    }
}
