<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${pageTitle} - Student Course Management</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f0f2f5;
            color: #333;
            min-height: 100vh;
        }

        /* ===== NAVBAR ===== */
        nav {
            background: linear-gradient(135deg, #1e3a8a 0%, #3b82f6 100%);
            padding: 0 2rem;
            box-shadow: 0 2px 10px rgba(0,0,0,0.2);
            position: sticky;
            top: 0;
            z-index: 1000;
        }
        .nav-inner {
            display: flex;
            align-items: center;
            justify-content: space-between;
            max-width: 1200px;
            margin: 0 auto;
            height: 60px;
        }
        .nav-brand {
            color: #fff;
            font-size: 1.25rem;
            font-weight: 700;
            text-decoration: none;
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }
        .nav-brand span { font-size: 1.4rem; }
        .nav-links { display: flex; gap: 0.25rem; }
        .nav-links a {
            color: rgba(255,255,255,0.85);
            text-decoration: none;
            padding: 0.45rem 1rem;
            border-radius: 6px;
            font-size: 0.9rem;
            font-weight: 500;
            transition: background 0.2s, color 0.2s;
        }
        .nav-links a:hover, .nav-links a.active {
            background: rgba(255,255,255,0.2);
            color: #fff;
        }

        /* ===== MAIN CONTENT ===== */
        .container {
            max-width: 1200px;
            margin: 2rem auto;
            padding: 0 1.5rem;
        }

        /* ===== ALERTS ===== */
        .alert {
            padding: 0.875rem 1.25rem;
            border-radius: 8px;
            margin-bottom: 1.5rem;
            display: flex;
            align-items: center;
            gap: 0.75rem;
            font-size: 0.95rem;
            font-weight: 500;
        }
        .alert-success {
            background: #dcfce7;
            color: #166534;
            border: 1px solid #bbf7d0;
        }
        .alert-danger {
            background: #fef2f2;
            color: #991b1b;
            border: 1px solid #fecaca;
        }
        .alert-icon { font-size: 1.1rem; }

        /* ===== CARDS ===== */
        .card {
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 1px 3px rgba(0,0,0,0.08), 0 4px 16px rgba(0,0,0,0.04);
            overflow: hidden;
        }
        .card-header {
            padding: 1.25rem 1.5rem;
            border-bottom: 1px solid #e5e7eb;
            display: flex;
            align-items: center;
            justify-content: space-between;
            background: #fafafa;
        }
        .card-header h2 {
            font-size: 1.1rem;
            font-weight: 600;
            color: #1e3a8a;
        }
        .card-body { padding: 1.5rem; }

        /* ===== PAGE HEADER ===== */
        .page-header {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-bottom: 1.5rem;
        }
        .page-header h1 {
            font-size: 1.6rem;
            font-weight: 700;
            color: #1e3a8a;
        }
        .page-subtitle {
            color: #6b7280;
            font-size: 0.9rem;
            margin-top: 0.2rem;
        }

        /* ===== BUTTONS ===== */
        .btn {
            display: inline-flex;
            align-items: center;
            gap: 0.4rem;
            padding: 0.5rem 1.1rem;
            border: none;
            border-radius: 7px;
            font-size: 0.875rem;
            font-weight: 600;
            cursor: pointer;
            text-decoration: none;
            transition: all 0.2s;
        }
        .btn-primary {
            background: #2563eb;
            color: #fff;
        }
        .btn-primary:hover { background: #1d4ed8; transform: translateY(-1px); }
        .btn-success {
            background: #16a34a;
            color: #fff;
        }
        .btn-success:hover { background: #15803d; }
        .btn-warning {
            background: #d97706;
            color: #fff;
        }
        .btn-warning:hover { background: #b45309; }
        .btn-danger {
            background: #dc2626;
            color: #fff;
        }
        .btn-danger:hover { background: #b91c1c; }
        .btn-secondary {
            background: #6b7280;
            color: #fff;
        }
        .btn-secondary:hover { background: #4b5563; }
        .btn-sm { padding: 0.35rem 0.75rem; font-size: 0.8rem; }
        .btn-outline {
            background: transparent;
            border: 2px solid #2563eb;
            color: #2563eb;
        }
        .btn-outline:hover { background: #2563eb; color: #fff; }

        /* ===== TABLE ===== */
        .table-wrapper { overflow-x: auto; }
        table {
            width: 100%;
            border-collapse: collapse;
            font-size: 0.9rem;
        }
        thead { background: #eff6ff; }
        thead th {
            padding: 0.875rem 1rem;
            text-align: left;
            font-size: 0.75rem;
            font-weight: 700;
            color: #1e3a8a;
            text-transform: uppercase;
            letter-spacing: 0.05em;
            border-bottom: 2px solid #bfdbfe;
        }
        tbody tr {
            border-bottom: 1px solid #f3f4f6;
            transition: background 0.15s;
        }
        tbody tr:hover { background: #f8fafc; }
        tbody td {
            padding: 0.875rem 1rem;
            color: #374151;
            vertical-align: middle;
        }
        .badge {
            display: inline-block;
            padding: 0.25rem 0.6rem;
            border-radius: 99px;
            font-size: 0.75rem;
            font-weight: 600;
        }
        .badge-blue { background: #dbeafe; color: #1e40af; }
        .badge-green { background: #dcfce7; color: #166534; }
        .badge-purple { background: #ede9fe; color: #5b21b6; }
        .badge-orange { background: #ffedd5; color: #9a3412; }

        /* ===== FORM ===== */
        .form-group { margin-bottom: 1.25rem; }
        .form-label {
            display: block;
            font-size: 0.875rem;
            font-weight: 600;
            color: #374151;
            margin-bottom: 0.4rem;
        }
        .form-label .required { color: #dc2626; margin-left: 2px; }
        .form-control {
            width: 100%;
            padding: 0.6rem 0.875rem;
            border: 1.5px solid #d1d5db;
            border-radius: 7px;
            font-size: 0.9rem;
            color: #111827;
            background: #fff;
            transition: border-color 0.2s, box-shadow 0.2s;
            outline: none;
        }
        .form-control:focus {
            border-color: #3b82f6;
            box-shadow: 0 0 0 3px rgba(59,130,246,0.15);
        }
        .form-control.is-invalid { border-color: #dc2626; }
        .invalid-feedback {
            color: #dc2626;
            font-size: 0.8rem;
            margin-top: 0.3rem;
        }
        .form-hint {
            color: #6b7280;
            font-size: 0.78rem;
            margin-top: 0.3rem;
        }
        .form-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 1.25rem; }
        @media(max-width: 640px) { .form-grid { grid-template-columns: 1fr; } }
        .form-actions {
            display: flex;
            gap: 0.75rem;
            margin-top: 2rem;
            padding-top: 1.5rem;
            border-top: 1px solid #e5e7eb;
        }

        /* ===== STATS ===== */
        .stats-row {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
            gap: 1rem;
            margin-bottom: 1.5rem;
        }
        .stat-card {
            background: #fff;
            border-radius: 10px;
            padding: 1.25rem;
            box-shadow: 0 1px 3px rgba(0,0,0,0.08);
            display: flex;
            align-items: center;
            gap: 1rem;
        }
        .stat-icon {
            width: 48px;
            height: 48px;
            border-radius: 10px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 1.4rem;
        }
        .stat-icon.blue { background: #dbeafe; }
        .stat-icon.green { background: #dcfce7; }
        .stat-icon.purple { background: #ede9fe; }
        .stat-icon.orange { background: #ffedd5; }
        .stat-value { font-size: 1.6rem; font-weight: 700; color: #111827; }
        .stat-label { font-size: 0.8rem; color: #6b7280; font-weight: 500; }

        /* ===== FOOTER ===== */
        footer {
            text-align: center;
            padding: 2rem;
            color: #9ca3af;
            font-size: 0.85rem;
            margin-top: 3rem;
        }

        /* ===== DETAIL VIEW ===== */
        .detail-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 1rem; }
        .detail-item { }
        .detail-label { font-size: 0.75rem; font-weight: 700; color: #6b7280; text-transform: uppercase; letter-spacing: 0.05em; }
        .detail-value { font-size: 0.95rem; color: #111827; margin-top: 0.2rem; }

        /* ===== EMPTY STATE ===== */
        .empty-state {
            text-align: center;
            padding: 3rem 2rem;
            color: #9ca3af;
        }
        .empty-state .empty-icon { font-size: 3rem; margin-bottom: 1rem; }
        .empty-state h3 { font-size: 1.1rem; color: #6b7280; margin-bottom: 0.5rem; }
        .empty-state p { font-size: 0.9rem; margin-bottom: 1.5rem; }
    </style>
</head>
<body>
<nav>
    <div class="nav-inner">
        <a class="nav-brand" href="${pageContext.request.contextPath}/">
            <span>🎓</span> Student Course MS
        </a>
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/">Home</a>
            <a href="${pageContext.request.contextPath}/students">Students</a>
            <a href="${pageContext.request.contextPath}/courses">Courses</a>
            <a href="${pageContext.request.contextPath}/students/enrollments">Enrollments</a>
        </div>
    </div>
</nav>
<div class="container">
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">
            <span class="alert-icon">✅</span> ${successMessage}
        </div>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">
            <span class="alert-icon">❌</span> ${errorMessage}
        </div>
    </c:if>
