<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h1 align="center">Employee List</h1>
<hr>

<table border="2" align="center">
<tr>
<th>Employee Name</th>
<th>Employee Address</th>
<th>Employee Email</th>
<th>Sent Mail</th>
</tr>

<c:forEach items="${employees}" var="employee">
<tr>
<td>${employee.emp_name}</td>
<td>${employee.emp_address}</td>
<td>${employee.emp_email}</td>
<td>
<a href="/sendMail/${employee.emp_id}">Mail</a>
</td>
</tr>
</c:forEach>

</table>