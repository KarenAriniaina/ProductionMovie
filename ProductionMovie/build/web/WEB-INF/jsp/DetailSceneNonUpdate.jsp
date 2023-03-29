<%-- 
    Document   : DetailSceneNonUpdate
    Created on : 16 mars 2023, 20:37:29
    Author     : Ari
--%>

<%@page import="modele.V_Deroulement"%>
<%@page import="modele.Emotion"%>
<%@page import="modele.Profil"%>
<%@page import="modele.Plateaux"%>
<%@page import="java.util.ArrayList"%>
<%@page import="modele.Scene"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

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
    Scene sc = (Scene) request.getAttribute("scene");
    ArrayList<Plateaux> lp = (ArrayList<Plateaux>) request.getAttribute("lp");
    ArrayList<Profil> la = (ArrayList<Profil>) request.getAttribute("la");
    ArrayList<Profil> lae = (ArrayList<Profil>) request.getAttribute("lae");
    ArrayList<Emotion> le = (ArrayList<Emotion>) request.getAttribute("le");
    ArrayList<V_Deroulement> ld = (ArrayList<V_Deroulement>) request.getAttribute("ld");
    Plateaux p = (Plateaux) request.getAttribute("p");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Detail scene</title>
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
        <section class="banner">
            <div class="container">
                <div class="banner-img">
                    <a href="#"><img src="css/images/cat/cinema.jpg" alt="" class="img-fluid w-100" ></a>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="banner-content text-center">
                            <div class="meta-cat">
                                <span class="text-capitalize letter-spacing-1 cat-name font-extra text-color">Scène</span>
                            </div>
                            <div class="post-title">
                                <h2><a href="#"><%= sc.getTitre()%></a></h2>
                            </div>

                            <div class="post-meta footer-meta">
                                <ul class="list-inline">
                                    <li class="post-read list-inline-item"><%= sc.getDureeScene()%></li>
                                    <li class="post-view list-inline-item"><%= p.getNom()%></li>
                                </ul>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </section>
        <div class="row" style="margin-top: 70px">
            <div class="col-md-2"></div>
            <div class="col-md-8 sidebar-widget subscribe mb-5">
                    <table class="table" id="tbActeur">
                        <tr>
                            <th>Acteur(s)</th>
                        </tr>
                        <% for (Profil pr : lae) {%>
                        <tr id="Acteur<%= pr.getId()%>">
                            <td><%= pr.getNom()%></td>
                        </tr>
                        <% } %>
                    </table>
                    <label>Deroulement</label>
                    <table class="table" id="tbDeroulement">
                        <tr>
                            <th>Emotion</th>
                            <th>Acteur</th>
                            <th>Texte</th>
                            <th>Duree</th>
                            <th>Ordre</th>
                        </tr>
                        <% for (V_Deroulement d : ld) {%>
                        <tr id="deroulement<%= d.getId()%>">    
                            <td><% if (d.getEmotion() != null) {%><%= d.getEmotion()%> <%} %></td>
                            <td><% if (d.getNomActeur() != null) {%><%= d.getNomActeur()%> <% }%></td>
                            <td><%= d.getTexte()%></td>
                            <td><%= d.getDuree()%></td>
                            <td><%= d.getOrdre()%></td>
                        </tr>
                        <% }%>

                    </table>
            </div>
        </div>
        <section class="footer-2 section-padding gray-bg">

            <div class="footer-btm">
                <div class="row justify-content-center">
                    <div class="col-lg-6">
                        <div class="copyright text-center ">
                             @ copyright all reserved to Karen ETU001445 - 2023
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
