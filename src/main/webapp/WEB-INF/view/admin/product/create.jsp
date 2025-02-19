<%@page contentType="text/html" pageEncoding="UTF-8" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
      <!DOCTYPE html>
      <html lang="en">

      <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="  - Dự án laptopshop" />
        <meta name="author" content=" " />
        <title>Create User</title>
        <link href="/css/styles.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <script>
          $(document).ready(() => {
            const productFile = $("#productFile");
            productFile.change(function (e) {
              const imgURL = URL.createObjectURL(e.target.files[0]);
              console.log(imgURL);
              $("#productPreview").attr("src", imgURL);
              $("#productPreview").css({ "display": "block" });
            });
          });
        </script>
      </head>

      <body class="sb-nav-fixed">
        <jsp:include page="../layout/header.jsp" />
        <div id="layoutSidenav">
          <jsp:include page="../layout/sidebar.jsp" />
          <div id="layoutSidenav_content">
            <main>
              <div class="container-fluid px-4">
                <h1 class="mt-4">Manage Product</h1>
                <ol class="breadcrumb mb-4">
                  <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
                  <li class="breadcrumb-item"><a href="/admin/user">List product</a></li>
                  <li class="breadcrumb-item active">Create product</li>
                </ol>
                <div class="container mt-1">
                  <div class="row">
                    <div class="col-md-6 col-12 mx-auto">
                      <p class="fs-1 border-bottom py-2">Create a product</p>
                      <form:form class="" method="post" action="/admin/product/create" modelAttribute="newProduct"
                        enctype="multipart/form-data">
                        <div class="row">
                          <div class="col-6 mb-3">
                            <label class="form-label">Name:</label>
                            <form:input type="text" class="form-control" path="name" />
                          </div>
                          <div class="col-6 mb-3">
                            <label class="form-label">Price:</label>
                            <form:input type="text" class="form-control" path="" />
                          </div>
                        </div>
                        <div class="row">
                          <div class="col-12 mb-3">
                            <label class="form-label">Detail description:</label>
                            <!-- <form:input type="text" class="form-control" path="" /> -->
                            <textarea class="form-control" placeholder="" id="" style="height: 80px"></textarea>
                          </div>
                        </div>
                        <div class="row">
                          <div class="col-6 mb-3">
                            <label class="form-label">Short description:</label>
                            <form:input type="text" class="form-control" path="" />
                          </div>
                          <div class="col-6 mb-3">
                            <label class="form-label">Quantity:</label>
                            <form:input type="text" class="form-control" path="" />
                          </div>
                        </div>
                        <div class="row">
                          <div class="col-6 mb-3">
                            <label class="form-label">Factory:</label>
                            <form:select class="form-select" path="">
                              <form:option value="">Apple (Macbook)</form:option>
                              <form:option value="">Asus</form:option>
                              <form:option value="">Lenovo</form:option>
                            </form:select>
                          </div>
                          <div class="col-6 mb-3">
                            <label class="form-label">Target:</label>
                            <form:select class="form-select" path="">
                              <form:option value="">Gaming</form:option>
                              <form:option value="">Sinh viên - văn phòng</form:option>
                              <form:option value="">Thiết kế đồ họa</form:option>
                            </form:select>
                          </div>
                        </div>
                        <div class="row">
                          <div class="col-6 mb-3">
                            <label for="productFile" class="form-label">Image:</label>
                            <input class="form-control" type="file" id="productFile" accept=".png, .jpg, .jpeg"
                              name="productFile" multiple="false" />
                          </div>
                        </div>
                        <div class="col-12">
                          <img style="max-height: 250px; object-fit: contain; display: none;" alt="product preview"
                            id="productPreview">
                        </div>
                        <div class="col-12 mt-2">
                          <button type="submit" class="btn btn-primary">Submit</button>
                        </div>
                      </form:form>
                    </div>
                  </div>
                </div>
              </div>
            </main>
            <jsp:include page="../layout/footer.jsp" />
          </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
          crossorigin="anonymous"></script>
        <script src="js/scripts.js"></script>
      </body>

      </html>