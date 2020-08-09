package com.listsentmail.controller;

import java.util.List;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.listsentmail.models.Employee;
import com.listsentmail.repository.EmployeeRepository;

@Controller
public class MainController {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	private JavaMailSender mailSenderObj;

	@GetMapping("/")
	public String welcome() {

		return "welcome";
	}

	@RequestMapping(value = { "/allemployees" }, method = RequestMethod.GET)
	public String viewEmployees(Model model) {

		List<Employee> allEmployees = employeeRepository.findAll();
		model.addAttribute("employees", allEmployees);
		return "employeelist";

	}

	// sendmail code
	@GetMapping(value = "/sendMail/{employee_id}")
	public ResponseEntity<Employee> employeeMail(@PathVariable("employee_id") Integer employee_id) {

		Employee employee = employeeRepository.findById(employee_id).get();

		sendmail(employee);

		return new ResponseEntity<Employee>(HttpStatus.OK);

	}

	private void sendmail(Employee employee) {

		final String emailToRecipient = employee.getEmp_email();
		final String emailSubject = "Succesfully Registration";

		final String emailMessage1 = "<html> <body> <p>Dear Sir/Madam,</p><p>You have succesfully Registered with our Services"
				+ "<br><br>"
				+ "<table border='1' width='300px' style='text-align:center;font-size:20px;'><tr> <td colspan='2'>"
				+ "</td></tr><tr><td>Name</td><td>" + employee.getEmp_name() + "</td></tr><tr><td>Address</td><td>"
				+ employee.getEmp_address() + "</td></tr><tr><td>Email</td><td>" + employee.getEmp_email()
				+ "</td></tr></table> </body></html>";

		mailSenderObj.send(new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {

				MimeMessageHelper mimeMsgHelperObj = new MimeMessageHelper(mimeMessage, true, "UTF-8");

				mimeMsgHelperObj.setTo(emailToRecipient);
				mimeMsgHelperObj.setText(emailMessage1, true);

				mimeMsgHelperObj.setSubject(emailSubject);

			}
		});

	}

}
