<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="page-header">
    <div>
        <h1>🔗 Student-Course Enrollments</h1>
        <p class="page-subtitle">
            Result of INNER JOIN between Students and Courses —
            ${fn:length(enrollments)} enrollment(s) found
        </p>
    </div>
    <a href="${pageContext.request.contextPath}/students" class="btn btn-secondary">← Back to Students</a>
</div>

<div class="card" style="margin-bottom:1rem; background:#eff6ff; border:1.5px solid #bfdbfe;">
    <div class="card-body" style="padding:1rem 1.5rem;">
        <strong style="color:#1e3a8a;">📋 Custom JPQL Query Used:</strong>
        <code style="display:block; margin-top:0.5rem; font-family:monospace; font-size:0.85rem; color:#374151; background:#fff; padding:0.75rem; border-radius:6px; border:1px solid #e5e7eb;">
            SELECT new com.example.entity.StudentCourseDTO(<br>
            &nbsp;&nbsp;&nbsp;&nbsp;s.id, s.name, s.email, s.department,<br>
            &nbsp;&nbsp;&nbsp;&nbsp;c.id, c.courseName, c.courseCode, c.instructor, c.credits<br>
            )<br>
            FROM Student s INNER JOIN s.courses c<br>
            ORDER BY s.name, c.courseName
        </code>
    </div>
</div>

<div class="card">
    <div class="card-header"><h2>Enrollment Records</h2></div>
    <div class="table-wrapper">
        <c:choose>
            <c:when test="${empty enrollments}">
                <div class="empty-state">
                    <div class="empty-icon">📭</div>
                    <h3>No enrollments found</h3>
                    <p>No students have been enrolled in any courses yet.</p>
                </div>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                        <tr>
                            <th>Student Name</th>
                            <th>Student Email</th>
                            <th>Student Dept.</th>
                            <th>Course Name</th>
                            <th>Course Code</th>
                            <th>Instructor</th>
                            <th>Credits</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="row" items="${enrollments}">
                            <tr>
                                <td><strong>${row.studentName}</strong></td>
                                <td style="color:#2563eb; font-size:0.85rem;">${row.studentEmail}</td>
                                <td><span class="badge badge-blue">${row.department}</span></td>
                                <td>${row.courseName}</td>
                                <td><span class="badge badge-green">${row.courseCode}</span></td>
                                <td style="font-size:0.85rem; color:#6b7280;">${row.instructor}</td>
                                <td><span class="badge badge-purple">${row.credits} cr</span></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
