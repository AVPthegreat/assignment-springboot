<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="page-header">
    <div>
        <h1>✏️ Edit Course</h1>
        <p class="page-subtitle">Update details for <strong>${course.courseName}</strong></p>
    </div>
    <a href="${pageContext.request.contextPath}/courses" class="btn btn-secondary">← Back to List</a>
</div>

<div class="card" style="max-width:720px; margin:0 auto;">
    <div class="card-header">
        <h2>Edit Course — ${course.courseCode}</h2>
    </div>
    <div class="card-body">

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">
                <span class="alert-icon">❌</span> ${errorMessage}
            </div>
        </c:if>

        <form:form method="post"
                   action="${pageContext.request.contextPath}/courses/edit/${course.id}"
                   modelAttribute="course" novalidate="true">

            <div class="form-group">
                <label class="form-label" for="courseName">Course Name <span class="required">*</span></label>
                <form:input path="courseName" id="courseName" cssClass="form-control"/>
                <form:errors path="courseName" cssClass="invalid-feedback"/>
            </div>

            <div class="form-grid">
                <div class="form-group">
                    <label class="form-label" for="courseCode">Course Code <span class="required">*</span></label>
                    <form:input path="courseCode" id="courseCode" cssClass="form-control"/>
                    <p class="form-hint">Format: 2-4 uppercase letters + 3-4 digits</p>
                    <form:errors path="courseCode" cssClass="invalid-feedback"/>
                </div>

                <div class="form-group">
                    <label class="form-label" for="credits">Credit Hours <span class="required">*</span></label>
                    <form:select path="credits" id="credits" cssClass="form-control">
                        <form:option value="1" label="1 Credit"/>
                        <form:option value="2" label="2 Credits"/>
                        <form:option value="3" label="3 Credits"/>
                        <form:option value="4" label="4 Credits"/>
                        <form:option value="5" label="5 Credits"/>
                        <form:option value="6" label="6 Credits"/>
                    </form:select>
                    <form:errors path="credits" cssClass="invalid-feedback"/>
                </div>
            </div>

            <div class="form-grid">
                <div class="form-group">
                    <label class="form-label" for="instructor">Instructor <span class="required">*</span></label>
                    <form:input path="instructor" id="instructor" cssClass="form-control"/>
                    <form:errors path="instructor" cssClass="invalid-feedback"/>
                </div>

                <div class="form-group">
                    <label class="form-label" for="department">Department <span class="required">*</span></label>
                    <form:select path="department" id="department" cssClass="form-control">
                        <form:option value="Computer Science" label="Computer Science"/>
                        <form:option value="Electronics" label="Electronics"/>
                        <form:option value="Mathematics" label="Mathematics"/>
                        <form:option value="Mechanical" label="Mechanical Engineering"/>
                        <form:option value="Civil" label="Civil Engineering"/>
                    </form:select>
                    <form:errors path="department" cssClass="invalid-feedback"/>
                </div>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-warning">💾 Update Course</button>
                <a href="${pageContext.request.contextPath}/courses" class="btn btn-secondary">Cancel</a>
            </div>
        </form:form>
    </div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
