package com.my.control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.my.dto.Customer;
import com.my.dto.ResultBean;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.service.CustomerService;

@RestController
@RequestMapping("customer/*")
public class CustomerController {
  @Autowired
  private CustomerService customerService;


  @PostMapping(value = {"signup"})
  public ResultBean<?> signup(@RequestBody Customer customer)
      throws ServletException, IOException, AddException {
    ResultBean<?> resultBean = new ResultBean();

    customerService.signup(customer);
    resultBean.setStatus(1);
    resultBean.setMessage("signup secceed");

    return resultBean;
  }

  @GetMapping(value = {"idduplicationcheck/{id}"})
  public ResultBean<?> idDuplicationCheck(@PathVariable String id)
      throws ServletException, IOException, FindException {
    ResultBean<?> resultBean = new ResultBean();
    Customer customer = customerService.idDuplicationCheck(id);
    if (customer == null && !id.equals("")) {
      resultBean.setStatus(1);
      resultBean.setMessage("Available ID");
    } else {
      resultBean.setStatus(0);
      resultBean.setMessage("Not available ID");
    }
    return resultBean;
  }

  @GetMapping(value = {"login"})
  public ResultBean<?> login(@RequestParam(name = "id", required = true) String id,
      @RequestParam(name = "password", required = true) String password, HttpSession session)
      throws ServletException, IOException, FindException {
    ResultBean<?> resultBean = new ResultBean();
    // business logic 호출

    Customer customer = customerService.login(id, password);
    resultBean.setStatus(1);
    resultBean.setMessage("login succeed");
    session.setAttribute("loginInfo", id);

    return resultBean;
  }

  @GetMapping(value = {"loginstatus"})
  public ResultBean<?> loginStatus(HttpSession session) throws ServletException, IOException {
    ResultBean<?> resultBean = new ResultBean();
    String id = (String) session.getAttribute("loginInfo");

    if (id == null) {
      resultBean.setStatus(0);
    } else {
      resultBean.setStatus(1);
    }
    return resultBean;
  }

  @GetMapping(value = {"logout"})
  private ResponseEntity<?> logout(HttpSession session) throws ServletException, IOException {
    session.removeAttribute("loginInfo"); // 속성만 제거, 객체는 살아있음
    String id = (String) session.getAttribute("loginInfo");
    // String path = "/frontend/html/css_js_layout.html";
    // RequestDispatcher rd = request.getRequestDispatcher(path); // 페이지를 path로 이동
    // rd.forward(request, response);
    if (id == null) {
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
