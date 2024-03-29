<%@page import="modele.Film"%>
<%@page import="modele.Emotion"%>
<%@page import="modele.Profil"%>
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
    ArrayList<Scene> lsc = (ArrayList<Scene>) request.getAttribute("lsc");
    ArrayList<Scene> lscp = (ArrayList<Scene>) request.getAttribute("lscp");
    Film f = (Film) request.getAttribute("Film");
%>


<html lang="en">

    <head>
        <meta charset="utf-8">
        <title>Demande de planification pour <%= f.getTitre()%></title>
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
    <script>
        let i = 1;
        var listeScene = [];
        function AjoutScene() {
            var tabScene = document.getElementById("tbScene");
            let val = document.getElementById("idScene").value.split("@");
            if (listeScene.includes(val[0])) {
                alert("Scene deja selectionné");
            } else {
                let btr = document.createElement("tr");
                btr.setAttribute("id", "Element" + i);
                let id = "Element" + i;
                let cell = document.createElement("td");
                let cell2 = document.createElement("td");
                let cellText = document.createTextNode(val[1]);
                let cellButton = document.createElement("a");
                let textButton = document.createTextNode("Supprimer");
                cellButton.setAttribute("class", "form-group btn btn-primary col-lg-5");
                cellButton.setAttribute("onclick", "supprimerScene('" + id + "','" + val[0] + "')");
                cellButton.appendChild(textButton);
                cell.appendChild(cellText);
                cell2.appendChild(cellButton);
                btr.appendChild(cell);
                btr.appendChild(cell2);
                tabScene.appendChild(btr);
                listeScene.push(val[0]);
                document.getElementById("lscene").value = listeScene;
                i++;
            }
        }
        function supprimerScene(id, value) {
            var tabScene = document.getElementById("tbScene");
            var sup = document.getElementById(id);
            let index = listeScene.indexOf(value);
            listeScene.splice(index, 1);
            tabScene.removeChild(sup);
            document.getElementById("lscene").value = listeScene;
        }
    </script>
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

        <div class="row" style="margin-top: 70px">
            <div class="col-md-2"></div>
            <div class="col-md-8 sidebar-widget subscribe mb-5">
                <h1 class="text-center widget-title">Demande de planification pour <%= f.getTitre()%></h1>
                <form action="<%= request.getContextPath()%>/DemandePlanificationSubmit" method="POST" id="form">
                    <label>Date Debut</label>
                    <input type="date" class="form-control" placeholder="Titre" name="Dd"/>
                    <label>Date Fin</label>
                    <input type="date" class="form-control" placeholder="Titre" name="Df"/>
                    <table class="table" id="tbScene">
                        <tr>
                            <th>Scene</th>
                            <th></th>
                        </tr>
                        <tr>
                            <td>
                                <select class="form-control " id="idScene">
                                    <option value="">Scene</option>
                                    <% for (Scene s : lsc) {%>
                                    <option value="<%= s.getId() + "@" + s.getTitre()%>"><%= s.getTitre()%></option>
                                    <% }%>
                                </select>
                            </td>
                            <td><a class="form-group btn btn-primary col-lg-5" onclick="AjoutScene()">Ajouter</a> </td>
                        </tr>
                    </table>
                    <input type="hidden" name="lscene" id="lscene" value=""/>
                    <input type="submit" class="btn btn-primary mt-3 col-lg-12" onclick="validation()" value="Valider" />
                    <% if (!lscp.isEmpty()) { %>
                    <table class="table">
                        <tr>
                            <th>Scene</th>
                            <th>Plateaux</th>
                            <th>Date planifié</th>
                        </tr>
                        <%for (Scene s : lscp) {%>
                        <tr>
                            <td><%= s.getTitre()%></td>
                            <td><%= s.getPCorrespondant().getNom() %></td>
                            <td><%= s.getPlanificationCorrespondante().getDate() %></td>
                        </tr>
                        <% } %>
                    </table>
                    <% }%>
                </form>
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
