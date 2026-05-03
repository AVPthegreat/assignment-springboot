<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="page-header">
    <div>
        <h1>🎓 Welcome to SCMS</h1>
        <p class="page-subtitle">Student Course Management System — Spring Boot + JPA + JSP</p>
    </div>
</div>

<div class="stats-row">
    <div class="stat-card">
        <div class="stat-icon blue">👨‍🎓</div>
        <div>
            <div class="stat-value">10</div>
            <div class="stat-label">Students Enrolled</div>
        </div>
    </div>
    <div class="stat-card">
        <div class="stat-icon green">📚</div>
        <div>
            <div class="stat-value">10</div>
            <div class="stat-label">Courses Available</div>
        </div>
    </div>
    <div class="stat-card">
        <div class="stat-icon purple">🔗</div>
        <div>
            <div class="stat-value">30+</div>
            <div class="stat-label">Enrollments</div>
        </div>
    </div>
    <div class="stat-card">
        <div class="stat-icon orange">🏫</div>
        <div>
            <div class="stat-value">3</div>
            <div class="stat-label">Departments</div>
        </div>
    </div>
</div>

<div style="display:grid; grid-template-columns:1fr 1fr; gap:1rem; margin-bottom:1.5rem;">
    <div class="card">
        <div class="card-header"><h2>👨‍🎓 Student Management</h2></div>
        <div class="card-body">
            <p style="color:#6b7280; margin-bottom:1.2rem; font-size:0.9rem;">
                View, add, edit, and manage all student records in the system.
            </p>
            <div style="display:flex; gap:0.5rem; flex-wrap:wrap;">
                <a href="${pageContext.request.contextPath}/students" class="btn btn-primary">View All Students</a>
                <a href="${pageContext.request.contextPath}/students/add" class="btn btn-success">➕ Add Student</a>
            </div>
        </div>
    </div>
    <div class="card">
        <div class="card-header"><h2>📚 Course Management</h2></div>
        <div class="card-body">
            <p style="color:#6b7280; margin-bottom:1.2rem; font-size:0.9rem;">
                Browse, create, update, and manage all available courses.
            </p>
            <div style="display:flex; gap:0.5rem; flex-wrap:wrap;">
                <a href="${pageContext.request.contextPath}/courses" class="btn btn-primary">View All Courses</a>
                <a href="${pageContext.request.contextPath}/courses/add" class="btn btn-success">➕ Add Course</a>
            </div>
        </div>
    </div>
</div>

<div class="card">
    <div class="card-header"><h2>🔗 Enrollment Overview (INNER JOIN)</h2></div>
    <div class="card-body">
        <p style="color:#6b7280; margin-bottom:1.2rem; font-size:0.9rem;">
            View the result of the custom INNER JOIN query between Students and Courses — showing all enrollments.
        </p>
        <a href="${pageContext.request.contextPath}/students/enrollments" class="btn btn-outline">
            View Student-Course Enrollments →
        </a>
    </div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
