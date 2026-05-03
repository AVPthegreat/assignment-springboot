<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="page-header">
    <div>
        <h1>✏️ Edit Student</h1>
        <p class="page-subtitle">Update the information for <strong>${student.name}</strong></p>
    </div>
    <a href="${pageContext.request.contextPath}/students" class="btn btn-secondary">← Back to List</a>
</div>

<div class="card" style="max-width:720px; margin:0 auto;">
    <div class="card-header">
        <h2>Edit Student — ID #${student.id}</h2>
    </div>
    <div class="card-body">

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">
                <span class="alert-icon">❌</span> ${errorMessage}
            </div>
        </c:if>

        <form:form method="post"
                   action="${pageContext.request.contextPath}/students/edit/${student.id}"
                   modelAttribute="student" novalidate="true">

            <div class="form-grid">
                <div class="form-group">
                    <label class="form-label" for="name">Full Name <span class="required">*</span></label>
                    <form:input path="name" id="name" cssClass="form-control"/>
                    <form:errors path="name" cssClass="invalid-feedback"/>
                </div>

                <div class="form-group">
                    <label class="form-label" for="email">Email Address <span class="required">*</span></label>
                    <form:input path="email" id="email" type="email" cssClass="form-control"/>
                    <form:errors path="email" cssClass="invalid-feedback"/>
                </div>
            </div>

            <div class="form-grid">
                <div class="form-group">
                    <label class="form-label" for="phone">Phone Number <span class="required">*</span></label>
                    <form:input path="phone" id="phone" cssClass="form-control"/>
                    <p class="form-hint">10 digits only</p>
                    <form:errors path="phone" cssClass="invalid-feedback"/>
                </div>

                <div class="form-group">
                    <label class="form-label" for="year">Academic Year <span class="required">*</span></label>
                    <form:select path="year" id="year" cssClass="form-control">
                        <form:option value="1" label="Year 1 (First Year)"/>
                        <form:option value="2" label="Year 2 (Second Year)"/>
                        <form:option value="3" label="Year 3 (Third Year)"/>
                        <form:option value="4" label="Year 4 (Fourth Year)"/>
                    </form:select>
                    <form:errors path="year" cssClass="invalid-feedback"/>
                </div>
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

            <div class="form-actions">
                <button type="submit" class="btn btn-warning">💾 Update Student</button>
                <a href="${pageContext.request.contextPath}/students" class="btn btn-secondary">Cancel</a>
            </div>
        </form:form>
    </div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
