<%@page contentType="text/html" pageEncoding="UTF-8" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
      <!DOCTYPE html>
      <html lang="en">

      <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Login - SB Admin</title>
        <link href="css/styles.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        <style>
          body {
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            background-color: #f8f9fa;
          }

          .access-denied-container {
            text-align: center;
            max-width: 600px;
            padding: 20px;
          }

          .error-code {
            font-size: 4rem;
            font-weight: bold;
            color: #dc3545;
          }

          .btn-home {
            margin-top: 20px;
          }
        </style>
      </head>

      <body class="">
        <div class="access-denied-container">
          <h1 class="error-code">403</h1>
          <h2>Access Denied</h2>
          <p class="lead">Sorry, you do not have permission to access this page.</p>
          <a href="/" class="btn btn-primary btn-home">Return to Home</a>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
          crossorigin="anonymous"></script>
        <script src="js/scripts.js"></script>
      </body>

      </html>