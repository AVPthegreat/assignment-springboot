<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="page-header">
    <div>
        <h1>👨‍🎓 Students</h1>
        <p class="page-subtitle">${fn:length(students)} students registered in the system</p>
    </div>
    <a href="${pageContext.request.contextPath}/students/add" class="btn btn-success">➕ Add New Student</a>
</div>

<div class="card">
    <div class="card-header">
        <h2>Student List</h2>
        <a href="${pageContext.request.contextPath}/students/enrollments" class="btn btn-primary btn-sm">
            View Enrollments
        </a>
    </div>
    <div class="table-wrapper">
        <c:choose>
            <c:when test="${empty students}">
                <div class="empty-state">
                    <div class="empty-icon">👤</div>
                    <h3>No students found</h3>
                    <p>Start by adding your first student to the system.</p>
                    <a href="${pageContext.request.contextPath}/students/add" class="btn btn-primary">Add Student</a>
                </div>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Phone</th>
                            <th>Department</th>
                            <th>Year</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="student" items="${students}" varStatus="status">
                            <tr>
                                <td style="color:#9ca3af; font-size:0.8rem;">${status.count}</td>
                                <td>
                                    <strong>${student.name}</strong>
                                </td>
                                <td style="color:#2563eb;">${student.email}</td>
                                <td>${student.phone}</td>
                                <td>
                                    <span class="badge badge-blue">${student.department}</span>
                                </td>
                                <td>
                                    <span class="badge badge-purple">Year ${student.year}</span>
                                </td>
                                <td>
                                    <div style="display:flex; gap:0.4rem; flex-wrap:wrap;">
                                        <a href="${pageContext.request.contextPath}/students/${student.id}"
                                           class="btn btn-secondary btn-sm">👁️ View</a>
                                        <a href="${pageContext.request.contextPath}/students/edit/${student.id}"
                                           class="btn btn-warning btn-sm">✏️ Edit</a>
                                        <form method="post" action="${pageContext.request.contextPath}/students/delete/${student.id}"
                                              onsubmit="return confirm('Delete ${student.name}? This cannot be undone.');" style="display:inline;">
                                            <button type="submit" class="btn btn-danger btn-sm">🗑️ Delete</button>
                                        </form>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
