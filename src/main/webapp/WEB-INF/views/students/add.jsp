<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="page-header">
    <div>
        <h1>➕ Add New Student</h1>
        <p class="page-subtitle">Fill in the details below to register a new student</p>
    </div>
    <a href="${pageContext.request.contextPath}/students" class="btn btn-secondary">← Back to List</a>
</div>

<div class="card" style="max-width:720px; margin:0 auto;">
    <div class="card-header"><h2>Student Information</h2></div>
    <div class="card-body">

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">
                <span class="alert-icon">❌</span> ${errorMessage}
            </div>
        </c:if>

        <form:form method="post" action="${pageContext.request.contextPath}/students/add"
                   modelAttribute="student" novalidate="true">

            <div class="form-grid">
                <div class="form-group">
                    <label class="form-label" for="name">Full Name <span class="required">*</span></label>
                    <form:input path="name" id="name" cssClass="form-control" placeholder="e.g. Arjun Sharma"/>
                    <form:errors path="name" cssClass="invalid-feedback"/>
                </div>

                <div class="form-group">
                    <label class="form-label" for="email">Email Address <span class="required">*</span></label>
                    <form:input path="email" id="email" type="email" cssClass="form-control"
                                placeholder="e.g. arjun@university.edu"/>
                    <form:errors path="email" cssClass="invalid-feedback"/>
                </div>
            </div>

            <div class="form-grid">
                <div class="form-group">
                    <label class="form-label" for="phone">Phone Number <span class="required">*</span></label>
                    <form:input path="phone" id="phone" cssClass="form-control" placeholder="10-digit number"/>
                    <p class="form-hint">Enter exactly 10 digits (no spaces or dashes)</p>
                    <form:errors path="phone" cssClass="invalid-feedback"/>
                </div>

                <div class="form-group">
                    <label class="form-label" for="year">Academic Year <span class="required">*</span></label>
                    <form:select path="year" id="year" cssClass="form-control">
                        <form:option value="" label="-- Select Year --"/>
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
                    <form:option value="" label="-- Select Department --"/>
                    <form:option value="Computer Science" label="Computer Science"/>
                    <form:option value="Electronics" label="Electronics"/>
                    <form:option value="Mathematics" label="Mathematics"/>
                    <form:option value="Mechanical" label="Mechanical Engineering"/>
                    <form:option value="Civil" label="Civil Engineering"/>
                </form:select>
                <form:errors path="department" cssClass="invalid-feedback"/>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-success">✅ Save Student</button>
                <a href="${pageContext.request.contextPath}/students" class="btn btn-secondary">Cancel</a>
            </div>
        </form:form>
    </div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
