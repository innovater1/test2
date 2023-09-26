package com.example.controller;

import com.example.service.NewsService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@WebServlet("*.news")
/* 뉴스 이미지 파일 업로드 처리를 위해 @MultipartConfig를 추가.
최대 파일 크기와 저장 위치를 지정. */
@MultipartConfig(maxFileSize = 2 * 1024 * 1024, location = "c:/upload")
public class NewsController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NewsService newsService = new NewsService();

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset = utf-8");

        String action = req.getParameter("action");
        if (action == null) {
            action = "list";
        }
        switch (action) {
            case "list":
                newsService.listNews(req);
                req.getRequestDispatcher("/WEB-INF/newsList.jsp").forward(req, resp);
                break;
            case "add":
                newsService.addNews(req);
                resp.sendRedirect("list.news?action=list");
                break;
            case "remove":
                newsService.removeNews(req);
                resp.sendRedirect("list.news?action=list");
                break;
            case "view":
                newsService.getNews(req);
                req.getRequestDispatcher("/WEB-INF/newsView.jsp").forward(req, resp);
                break;
        }
    }
}
