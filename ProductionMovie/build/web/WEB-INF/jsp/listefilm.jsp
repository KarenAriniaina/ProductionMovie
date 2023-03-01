<%@page import="modele.Film"%>
<%@page import="modele.Plateaux"%>
<%@page import="java.util.ArrayList"%>
<%@page import="modele.Scene"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<!--
 // WEBSITE: https://themefisher.com
 // TWITTER: https://twitter.com/themefisher
 // FACEBOOK: https://www.facebook.com/themefisher
 // GITHUB: https://github.com/themefisher/
-->

<%
    int sAuteur = 0;
    if (session.getAttribute("idAuteur") != null) {
        sAuteur = (int) session.getAttribute("idAuteur");
    }
    int sRealisateur = 0;
    if (session.getAttribute("idRealisateur") != null) {
        sRealisateur = (int) session.getAttribute("idRealisateur");

    }
    int sActeur = 0;
    if (session.getAttribute("idActeur") != null) {
        sActeur = (int) session.getAttribute("idActeur");
    }

    int nbrPage = (int) request.getAttribute("nbrPage");
    int currentPage = (int) request.getAttribute("currentpage");
    ArrayList<Film> lf = (ArrayList<Film>) request.getAttribute("lf");
%>


<html lang="en">

    <head>
        <meta charset="utf-8">
        <title>Liste scènes</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <!--Favicon-->
        <link rel="shortcut icon" href="css/images/favicon.ico" type="image/x-icon">

        <!-- THEME CSS
            ================================================== -->
        <!-- Bootstrap -->
        <link rel="stylesheet" href="css/plugins/bootstrap/css/bootstrap.min.css">
        <!-- Themify -->
        <link rel="stylesheet" href="css/plugins/themify/css/themify-icons.css">
        <link rel="stylesheet" href="css/plugins/slick-carousel/slick-theme.css">
        <link rel="stylesheet" href="css/plugins/slick-carousel/slick.css">
        <!-- Slick Carousel -->
        <link rel="stylesheet" href="css/plugins/owl-carousel/owl.carousel.min.css">
        <link rel="stylesheet" href="css/plugins/owl-carousel/owl.theme.default.min.css">
        <link rel="stylesheet" href="css/plugins/magnific-popup/magnific-popup.css">
        <!-- manin stylesheet -->
        <link rel="stylesheet" href="css/css/style.css">
    </head>
    <body>
        <header class="header-top bg-dark">
            <div class="container">
                <nav class="navbar navbar-expand-lg navigation menu-white">
                    <a class="navbar-brand" href="<%= request.getContextPath()%>/">
                        <img src="css/images/logo-w.png" alt="" class="img-fluid">
                    </a>
                    <div class="collapse navbar-collapse" id="navbar-collapse">
                        <ul class="menu navbar-nav ml-auto">
                            <li class="nav-item"><a href="<%= request.getContextPath()%>/ListeFilm" class="nav-link">Films</a></li>
                            <li class="nav-item"><a href="<%= request.getContextPath()%>/" class="nav-link">Scènes</a></li>
                                <% if (sAuteur != 0) {%>
                            <li class="nav-item"><a href="<%= request.getContextPath()%>/AvantAjoutScene" class="nav-link">Ajout de scène</a></li>
                                <%  } %>
                                <% if (sActeur == 0 && sAuteur == 0 && sRealisateur == 0) {%>
                            <li class="nav-item"><a href="<%= request.getContextPath()%>/Connexion" class="nav-link">Connexion</a></li>
                                <% } %>
                                <% if (sActeur != 0 || sAuteur != 0 || sRealisateur != 0) {%>
                            <li class="nav-item"><a href="<%= request.getContextPath()%>/Deconnexion" class="nav-link">Deconnexion</a></li>
                                <% }%>
                        </ul>
                    </div>
                    <% if (sActeur != 0) {%>
                    <span>Acteur <%= session.getAttribute("Nom")%></span>   
                    <% }%>
                    <% if (sAuteur != 0) {%>
                    <span>Auteur <%= session.getAttribute("Nom")%></span>   
                    <% }%>
                    <% if (sRealisateur != 0) {%>
                    <span>Realisateur <%= session.getAttribute("Nom")%></span>   
                    <% }%>
                </nav>
            </div>
        </header>

        <section class="section-padding">
            <div class="container">
                <div class="row">
                <% if (!lf.isEmpty()) {%>

                <% for (Film f : lf) {%>
                <div class="col-lg-4 col-md-4 col-sm-6 mb-5">
                    <div class="category-item">
                        <div class="category-img">
                            <a href="<%= request.getContextPath()%>/Film?idFilm=<%= f.getId() %>"><img src="css/images/cat/cinema.jpg" alt="" class="img-fluid w-100"></a>
                        </div>
                        <div class="content">
                            <h3 class="text-color text-uppercase font-sm letter-spacing font-extra">Titre</h3>
                            <h4><a href="<%= request.getContextPath()%>/Film?idFilm=<%= f.getId() %>"><%= f.getTitre()%></a></h4>
                        </div>
                    </div>
                </div>
                <% } %>
                <% }%>
                </div>
            </div>

        </div>
    </section>
    <div class="m-auto">
        <div class="pagination mt-5 pt-4" style="margin-left: 650px">
            <ul class="list-inline">
                <%
                    if (nbrPage >= 1) {
                        if (currentPage != 1) {%>
                <li class="list-inline-item"><a href="<%= request.getContextPath()%>/ListeFilm?currentpage=<%= currentPage - 1%>"><i class="ti-arrow-left"></i></a></li>
                        <% } %>
                        <% for (int i = 0; i < nbrPage; i++) { %>
                        <% if (i + 1 == currentPage) {%>
                <li class="list-inline-item"><a href="<%= request.getContextPath()%>/ListeFilm?currentpage=<%= i + 1%>" class="active"><%= i + 1%></a></li>
                    <% } else {%>
                <li class="list-inline-item"><a href="<%= request.getContextPath()%>/ListeFilm?currentpage=<%= i + 1%>"><%= i + 1%></a></li>
                    <% } %>
                    <% } %>
                    <% if (currentPage != nbrPage) {%>
                <li class="list-inline-item"><a href="<%= request.getContextPath()%>/ListeFilm?currentpage=<%= currentPage + 1%>" class="prev-posts"><i class="ti-arrow-right"></i></a></li>
                        <% } %>
                        <% }%>
            </ul>
        </div>
    </div>
    <section class="footer-2 section-padding gray-bg">

        <div class="footer-btm">
            <div class="row justify-content-center">
                <div class="col-lg-6">
                    <div class="copyright text-center ">
                        @ copyright all reserved to Karen - 2023
                    </div>
                </div>
            </div>
        </div>
    </section>	


    <!-- THEME JAVASCRIPT FILES
    ================================================== -->
    <!-- initialize jQuery Library -->
    <script src="css/plugins/jquery/jquery.js"></script>
    <!-- Bootstrap jQuery -->
    <script src="css/plugins/bootstrap/js/bootstrap.min.js"></script>
    <script src="css/plugins/bootstrap/js/popper.min.js"></script>
    <!-- Owl caeousel -->
    <script src="css/plugins/owl-carousel/owl.carousel.min.js"></script>
    <script src="css/plugins/slick-carousel/slick.min.js"></script>
    <script src="css/plugins/magnific-popup/magnific-popup.js"></script>
    <!-- Instagram Feed Js -->
    <script src="css/plugins/instafeed-js/instafeed.min.js"></script>
    <!-- Google Map -->
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCC72vZw-6tGqFyRhhg5CkF2fqfILn2Tsw"></script>
    <script src="css/plugins/google-map/gmap.js"></script>
    <!-- main js -->
    <script src="css/js/custom.js"></script>
</body>
</html>

