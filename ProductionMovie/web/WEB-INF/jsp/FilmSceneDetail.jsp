<%@page import="modele.PropositionPlanning"%>
<%@page import="modele.Planification"%>
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
    int p = 1;
    ArrayList<PropositionPlanning> lpl = (ArrayList<PropositionPlanning>) request.getAttribute("lp");
%>


<html lang="en">

    <head>
        <meta charset="utf-8">
        <title>Liste scènes optimisés</title>
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
        <script>
            function CheckJour(j, p, nbrP) {
                var jour = document.getElementById("checkJour" + j).checked;
                if (jour === true) {
                    for (let i = 0; i < nbrP; i++) {
                        document.getElementById("checkPlateau" + j + (p + i)).checked = true;
                        var elements = document.getElementsByTagName("*");
                        var filteredElements = [];
                        for (var e = 0; e < elements.length; e++) {
                            var element = elements[e];
                            var id = element.getAttribute("id");
                            if (id && id.startsWith("checkScene" + (p + i))) {
                                element.checked = true;
                            }
                        }

                    }
                } else {
                    for (let i = 0; i < nbrP; i++) {
                        document.getElementById("checkPlateau" + j + (p + i)).checked = false;
                        var elements = document.getElementsByTagName("*");
                        var filteredElements = [];
                        for (var e = 0; e < elements.length; e++) {
                            var element = elements[e];
                            var id = element.getAttribute("id");
                            if (id && id.startsWith("checkScene" + (p + i))) {
                                element.checked = false;
                            }
                        }
                    }
                }
                //alert(j);
            }
            function CheckPlateau(j, p, nbr) {
                //alert(p);
                var cj = document.getElementById("checkJour" + j);
                var cp = document.getElementById("checkPlateau" + j + p).checked;
                //console.log(document.getElementById("checkPlateau" + j + p).checked);
                if (cp === true) {
                    for (let i = 1; i <= nbr; i++) {
                        document.getElementById("checkScene" + p + "-" + i).checked = true;
                    }
                } else {
                    cj.checked = false;
                    for (let i = 1; i <= nbr; i++) {
                        document.getElementById("checkScene" + p + "-" + i).checked = false;
                    }
                }

            }
            function CheckScene(s, p, j, nbrScene, nbrP) {
                //alert(p);
                var cj = document.getElementById("checkJour" + j);
                var cp = document.getElementById("checkPlateau" + j + p);
                var cs = document.getElementById("checkScene" + p + "-" + s).checked;
                //console.log(document.getElementById("checkPlateau" + j + p).checked);
                if (cs === false) {
                    cp.checked = false;
                    cj.checked = false;
                } else {
                    var elements = document.getElementsByTagName("*");
                    var filteredElements = [];
                    let n = 0;
                    for (var e = 0; e < elements.length; e++) {
                        let element = elements[e];
                        let id = element.getAttribute("id");
                        if (id && id.startsWith("checkScene" + p)) {
                            if (element.checked === true)
                                n++;
                        }
                    }
                    if (n == nbrScene) {
                        cp.checked = true;
                        var element = document.getElementsByTagName("*");
                        let nb = 0;
                        for (var i = 0; i < element.length; i++) {
                            let elementt = element[i];
                            let id = elementt.getAttribute("id");
                            if (id && id.startsWith("checkPlateau" + j)) {
                                if (elementt.checked === true)
                                    nb++;
                            }
                        }
                        if (nb == nbrP)
                            cj.checked = true;
                    }
                }
            }
            function validation(p) {
                var listeid = [];
                var element = document.getElementsByTagName("*");
                let nb = 0;
                for (let j = 1; j <= p; j++) {
                    for (var i = 0; i < element.length; i++) {
                        let elementt = element[i];
                        let id = elementt.getAttribute("id");
                        if (id && id.startsWith("checkScene" + j)) {
                            if (elementt.checked === true) {
                                let idP = elementt.getAttribute("id").split("-")[1];
                                listeid.push(document.getElementById("Scene" + j + idP).value)
                            }
                        }
                    }
                }
                document.getElementById("lidP").value = listeid.toString();
                //alert(document.getElementById("lidP").value)
            }
        </script>
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

        <section class="section-padding pt-4">
            <div class="container">
                <div class="row">
                    <div class="col-lg-8 col-md-12 col-sm-12 col-xs-12">
                        <form action="<%= request.getContextPath()%>/PlanificationSubmit" method="POST">
                            <%
                                if (!lpl.isEmpty()) {
                                    int j = 1;
                                    int nbrp = 0;
                                    for (PropositionPlanning pl : lpl) {
                                        if (pl.getListePlanifications() != null && !pl.getListePlanifications().isEmpty()) {
                                            nbrp = pl.getListePlanifications().size();
                                        }
                            %>

                            <article class="post-list mb-5 pb-4 border-bottom">
                                <div class="meta-cat">
                                    <span class="letter-spacing cat-name font-extra text-uppercase font-sm">Schedule Jour <%= pl.getDate()%></span>
                                    <input type="checkbox" name="checkJour" id="checkJour<%= j%>" onchange="CheckJour(<%= j%>,<%= p%>,<%= nbrp%>)"/>
                                </div>
                            </article>
                            <% if (pl.getListePlanifications() != null && !pl.getListePlanifications().isEmpty()) {  %>
                            <% for (ArrayList<Planification> lp : pl.getListePlanifications()) {
                                    int s = 1;%>
                            <article class="post-list mb-5 pb-4 border-bottom">
                                <div class="meta-cat">
                                    <span class="letter-spacing cat-name font-extra text-uppercase font-sm"><%= lp.get(0).getPlateauxTourner().getNom()%></span>
                                    <input type="checkbox" name="checkPlateaux" id="checkPlateau<%= j%><%=p%>" onchange="CheckPlateau(<%= j%>,<%= p%>,<%= lp.size()%>)"/>
                                </div>
                            </article>
                            <% for (Planification pa : lp) {%>
                            <div class="mb-4 post-list border-bottom pb-4">
                                <div class="row no-gutters">
                                    <div class="col-md-5">
                                        <a class="post-thumb " href="<%= request.getContextPath()%>/DetailScene?id=<%= pa.getSceneTourner().getId()%>">
                                            <img src="css/images/cat/cinema.jpg" alt="" class="img-fluid w-100">
                                        </a>
                                    </div>

                                    <div class="col-md-7">
                                        <div class="post-article mt-sm-3">
                                            <div class="meta-cat">
                                                <span
                                                    class="letter-spacing cat-name font-extra text-uppercase font-sm">Scene </span>
                                            </div>
                                            <h3 class="post-title mt-2"><a href="<%= request.getContextPath()%>/DetailScene?id=<%= pa.getSceneTourner().getId()%>"><%= pa.getSceneTourner().getTitre()%></a>
                                                <input type="checkbox" name="checkScene" id="checkScene<%= p%>-<%= s%>" onchange="CheckScene(<%= s%>,<%= p%>,<%= j%>,<%= lp.size()%>,<%= nbrp%>)" /></h3>
                                            <input type="hidden" id="Scene<%= p%><%= s%>" value="<%= pa.getId()%>"/>
                                            <div class="post-meta">
                                                <ul class="list-inline">
                                                    <li class="post-like list-inline-item">
                                                        <span class="font-sm letter-spacing-1 text-uppercase"><i
                                                                class="ti-time mr-2"></i> <%= pa.getSceneTourner().getDureeScene()%></span>
                                                    </li>   
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <% s++;
                                } %>
                            <% p++;
                                } %>
                            <% } %>
                            <% j++;
                                } %>
                            <% } else { %>
                            <p>vide</p>
                            <% }%>
                            <input type="hidden" value="" name="lidP" id="lidP"/>
                            <input type="submit" class="btn btn-primary mt-3 col-lg-12" onclick="validation(<%= p%>)" value="Valider" />
                        </form>
                    </div>

                </div>
            </div>
        </section>
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
