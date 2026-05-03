<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="page-header">
    <div>
        <h1>📚 Courses</h1>
        <p class="page-subtitle">${fn:length(courses)} courses available in the system</p>
    </div>
    <a href="${pageContext.request.contextPath}/courses/add" class="btn btn-success">➕ Add New Course</a>
</div>

<div class="card">
    <div class="card-header">
        <h2>Course List</h2>
        <a href="${pageContext.request.contextPath}/courses/active" class="btn btn-primary btn-sm">
            Active Courses (INNER JOIN)
        </a>
    </div>
    <div class="table-wrapper">
        <c:choose>
            <c:when test="${empty courses}">
                <div class="empty-state">
                    <div class="empty-icon">📖</div>
                    <h3>No courses found</h3>
                    <p>Start by adding your first course to the system.</p>
                    <a href="${pageContext.request.contextPath}/courses/add" class="btn btn-primary">Add Course</a>
                </div>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Course Name</th>
                            <th>Code</th>
                            <th>Instructor</th>
                            <th>Department</th>
                            <th>Credits</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="course" items="${courses}" varStatus="status">
                            <tr>
                                <td style="color:#9ca3af; font-size:0.8rem;">${status.count}</td>
                                <td><strong>${course.courseName}</strong></td>
                                <td><span class="badge badge-green">${course.courseCode}</span></td>
                                <td style="color:#6b7280; font-size:0.85rem;">${course.instructor}</td>
                                <td><span class="badge badge-blue">${course.department}</span></td>
                                <td>
                                    <span class="badge badge-orange">${course.credits} cr</span>
                                </td>
                                <td>
                                    <div style="display:flex; gap:0.4rem; flex-wrap:wrap;">
                                        <a href="${pageContext.request.contextPath}/courses/${course.id}"
                                           class="btn btn-secondary btn-sm">👁️ View</a>
                                        <a href="${pageContext.request.contextPath}/courses/edit/${course.id}"
                                           class="btn btn-warning btn-sm">✏️ Edit</a>
                                        <form method="post"
                                              action="${pageContext.request.contextPath}/courses/delete/${course.id}"
                                              onsubmit="return confirm('Delete ${course.courseName}?');"
                                              style="display:inline;">
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
