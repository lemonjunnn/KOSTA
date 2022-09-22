package com.my.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.dto.Customer;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.service.CustomerService;

public class CustomerController implements Controller {

  private CustomerService customerService;

  public CustomerController() {
    this.customerService = new CustomerService();
  }

  public String execute(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String servletPath = request.getServletPath();
    if ("/signup".equals(servletPath)) {
      return signup(request, response);
    } else if ("/idduplicationcheck".equals(servletPath)) {
      return idDuplicationCheck(request, response);
    } else if ("/login".equals(servletPath)) {
      return login(request, response);
    } else if ("/loginstatus".equals(servletPath)) {
      return loginStatus(request, response);
    } else if ("/logout".equals(servletPath)) {
      return logout(request, response);
    }
    return null;
  }

  private String signup(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");// 응답 형식 설정 (MIME;encoding)
    ObjectMapper mapper = new ObjectMapper(); // 객체를 json 형식으로 바꾸기
    Map<String, Object> map = new HashMap<String, Object>();
    Customer customer = new Customer();
    String id = request.getParameter("id");
    String password = request.getParameter("password");
    String name = request.getParameter("name");
    String address = request.getParameter("address");
    int buildingno = Integer.parseInt(request.getParameter("buildingno"));
    customer.setId(id);
    customer.setPassword(password);
    customer.setName(name);
    customer.setAddress(address);
    customer.setBuildingno(buildingno);
    try {
      customerService.signup(customer);
      map.put("status", 1);
      map.put("message", "signup succeed");
    } catch (AddException e) {
      map.put("status", 0);
      map.put("message", "signup failed.");
      e.printStackTrace();
    } catch (Exception e) {
      map.put("status", 0);
      map.put("message", "signup failed.");
      e.printStackTrace();
    }
    String result = mapper.writeValueAsString(map);
    System.out.println("signup() in CustomerController : " + result);
    return result;
  }

  private String idDuplicationCheck(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");// 응답 형식 설정 (MIME;encoding)
    ObjectMapper mapper = new ObjectMapper(); // 객체를 json 형식으로 바꾸기
    Map<String, Object> map = new HashMap<String, Object>();
    String id = request.getParameter("id");
    try {
      Customer customer = customerService.idDuplicationCheck(id);
      if (customer == null && !id.equals("")) {
        map.put("status", 1);
        map.put("message", "사용이 가능한 ID입니다.");
      } else {
        map.put("status", 0);
        map.put("message", "사용이 불가능한 ID입니다.");
      }
    } catch (FindException e) {
      map.put("status", 0);
      map.put("message", "연결오류. 다시 실행하세요");
      e.printStackTrace();
    } catch (Exception e) {
      map.put("status", 0);
      map.put("message", "연결오류. 다시 실행하세요");
      e.printStackTrace();
    }
    String result = mapper.writeValueAsString(map);
    System.out.println("idDuplicationCheck() in CustomerController : " + result);
    return result;
  }

  private String login(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");
    HttpSession session = request.getSession();
    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> map = new HashMap<String, Object>();

    String id = request.getParameter("id");
    String password = request.getParameter("password");
    System.out.println(id + " / " + password);
    // business logic 호출
    try {
      Customer customer = customerService.login(id, password);
      map.put("status", 1);
      map.put("message", "login succeed");
      session.setAttribute("loginInfo", id);
    } catch (FindException e) {
      map.put("status", 0);
      map.put("message", "login failed");
      e.printStackTrace();
    } catch (Exception e) {
      map.put("status", 0);
      map.put("message", "login failed");
      e.printStackTrace();
    }

    String result = mapper.writeValueAsString(map);
    System.out.println("login() in CustomerController : " + result);
    return result;
  }

  private String loginStatus(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");// 응답 형식 설정 (MIME;encoding)
    ObjectMapper mapper = new ObjectMapper(); // 객체를 json 형식으로 바꾸기
    Map<String, Object> map = new HashMap<String, Object>();
    HttpSession session = request.getSession();
    String id = (String) session.getAttribute("loginInfo");

    if (id == null) {
      map.put("status", 0);
    } else {
      map.put("status", 1);
    }
    String result = mapper.writeValueAsString(map);
    System.out.println("loginStatus() in CustomerController : " + result);
    return result;
  }

  private String logout(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");// 응답 형식 설정 (MIME;encoding)
    HttpSession session = request.getSession();
    session.removeAttribute("loginInfo"); // 속성만 제거, 객체는 살아있음
    String id = (String) session.getAttribute("loginInfo");
    // String path = "/frontend/html/css_js_layout.html";
    // RequestDispatcher rd = request.getRequestDispatcher(path); // 페이지를 path로 이동
    // rd.forward(request, response);
    return null;
  }
}
