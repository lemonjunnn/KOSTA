package com.my.control;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DispatcherServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");
    PrintWriter out = response.getWriter();
    // get current url path
    String servletPath = request.getServletPath();
    ServletContext servletContext = getServletContext();
    String envPath = servletContext.getRealPath("my.properties");
    System.out.println("servletPath : " + servletPath + "  / envPath : " + envPath);

    Properties env = new Properties();
    env.load(new FileInputStream(envPath));
    String clazzName = env.getProperty(servletPath);
    Controller control = null;
    String result = null;
    try {
      Class clazz = Class.forName(clazzName); // JVM에 classfile(CustomerController 등)을 load
      control = (Controller) clazz.newInstance();
      result = control.execute(request, response);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    out.print(result);
  }
}
