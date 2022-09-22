package com.my.control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrderController implements Controller {
  public String execute(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String servletPath = request.getServletPath();
    if ("/productlist".equals(servletPath)) {
      return null;
    } else if ("/viewproduct".equals(servletPath)) {
      return null;
    }
    return null;
  }
}
