package com.my.control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.my.dto.Customer;
import com.my.dto.ResultBean;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.service.CustomerService;

@Controller
public class CustomerController {
  @Autowired
  private CustomerService customerService;


  @PostMapping(produces = "application/json;charset=UTF-8", value = "signup")
  @ResponseBody
  public ResultBean<Object> signup(@RequestBody Customer customer)
      throws ServletException, IOException {
    ResultBean<Object> resultBean = new ResultBean<Object>();
    try {
      customerService.signup(customer);
      resultBean.setStatus(1);
      resultBean.setMessage("signup secceed");
    } catch (AddException e) {
      resultBean.setMessage("signup failed");
      e.printStackTrace();
    } catch (Exception e) {
      resultBean.setMessage("signup failed");
      e.printStackTrace();
    }
    return resultBean;
  }

  @PostMapping(produces = "application/json;charset=UTF-8", value = "idduplicationcheck")
  @ResponseBody
  public ResultBean<Object> idDuplicationCheck(
      @RequestParam(name = "id", required = true) String id) throws ServletException, IOException {
    ResultBean<Object> resultBean = new ResultBean<Object>();
    try {
      Customer customer = customerService.idDuplicationCheck(id);
      if (customer == null && !id.equals("")) {
        resultBean.setStatus(1);
        resultBean.setMessage("Available ID");
      } else {
        resultBean.setMessage("Not available ID");
      }
    } catch (FindException e) {
      resultBean.setMessage("Not available ID");
      e.printStackTrace();
    } catch (Exception e) {
      resultBean.setMessage("Not available ID");
      e.printStackTrace();
    }
    return resultBean;
  }

  @PostMapping(produces = "application/json;charset=UTF-8", value = "login")
  @ResponseBody
  public ResultBean<Object> login(@RequestParam(name = "id", required = true) String id,
      @RequestParam(name = "password", required = true) String password, HttpSession session)
      throws ServletException, IOException {
    ResultBean<Object> resultBean = new ResultBean<Object>();

    System.out.println(id + " / " + password);
    // business logic 호출
    try {
      Customer customer = customerService.login(id, password);
      resultBean.setStatus(1);
      resultBean.setMessage("login succeed");
      session.setAttribute("loginInfo", id);
    } catch (FindException e) {
      resultBean.setMessage("login failed");
      e.printStackTrace();
    } catch (Exception e) {
      resultBean.setMessage("login failed");
      e.printStackTrace();
    }
    return resultBean;
  }

  @PostMapping(produces = "application/json;charset=UTF-8", value = "loginstatus")
  public ResultBean<Object> loginStatus(HttpSession session) throws ServletException, IOException {
    ResultBean<Object> resultBean = new ResultBean<Object>();
    String id = (String) session.getAttribute("loginInfo");

    if (id == null) {
      resultBean.setStatus(1);
    }
    return resultBean;
  }

  @PostMapping(produces = "application/json;charset=UTF-8", value = "logout")
  private String logout(HttpSession session) throws ServletException, IOException {
    session.removeAttribute("loginInfo"); // 속성만 제거, 객체는 살아있음
    String id = (String) session.getAttribute("loginInfo");
    System.out.println("session의 loginInfo attribute 삭제 여부 확인 : " + id);
    // String path = "/frontend/html/css_js_layout.html";
    // RequestDispatcher rd = request.getRequestDispatcher(path); // 페이지를 path로 이동
    // rd.forward(request, response);
    return null;
  }
}
