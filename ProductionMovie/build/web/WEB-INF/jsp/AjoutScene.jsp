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
    ArrayList<Plateaux> lp = (ArrayList<Plateaux>) request.getAttribute("lp");
    ArrayList<Profil> la = (ArrayList<Profil>) request.getAttribute("la");
    ArrayList<Emotion> le = (ArrayList<Emotion>) request.getAttribute("le");
    int idFilm = (int) request.getAttribute("idFilm");
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
    <script>
        let i = 1;
        var listeActeur = [];
        function AjoutActeur() {
            var tabActeur = document.getElementById("tbActeur");
            var selectActeur = document.getElementById("idActeur");
            let selected = selectActeur.value.split(" ");
            if (!listeActeur.includes(selected[0])) {
                let btr = document.createElement("tr");
                btr.setAttribute("id", "Element" + i);
                let id = "Element" + i;
                let cell = document.createElement("td");
                let cell2 = document.createElement("td");
                let cellText = document.createTextNode(selected[1]);
                let cellButton = document.createElement("a");
                let textButton = document.createTextNode("Supprimer");
                cellButton.setAttribute("class", "form-group btn btn-primary col-lg-3");
                cellButton.setAttribute("onclick", "supprimerActeur('" + id + "','" + selected[0] + "')");
                cellButton.appendChild(textButton);
                cell.appendChild(cellText);
                cell2.appendChild(cellButton);
                btr.appendChild(cell);
                btr.appendChild(cell2);
                tabActeur.appendChild(btr);
                listeActeur.push(selected[0]);
                console.log(listeActeur.toString());
                document.getElementById("lacteur").value = listeActeur;
                i++;
            } else {
                alert("Acteur deja existant");
            }
        }
        function supprimerActeur(test, id) {
            var tabActeur = document.getElementById("tbActeur");
            var sup = document.getElementById(test);
            let index = listeActeur.indexOf(id);
            listeActeur.splice(index, 1);
            tabActeur.removeChild(sup);
            document.getElementById("nbrscenario").value = d;
            document.getElementById("lacteur").value = listeActeur;
        }

        function createInput(id, value) {
            let input = document.createElement("input");
            input.setAttribute("id", id);
            input.setAttribute("value", value);
            input.setAttribute("name", id);
            input.setAttribute("type", "hidden");
            return input;
        }

        var d = 1;
        var nbr = 0;
        let listeOrdre = [];
        function AjouterDeroulement() {
            var tabDeroulement = document.getElementById("tbDeroulement");
            var form = document.getElementById("form");
            var emotion = document.getElementById("Emotion").value;
            var Acteur = document.getElementById("idActeurDialogue").value;
            var Ordre = document.getElementById("Ordre").value;
            var Duree = document.getElementById("Duree").value;
            console.log("Acteur=" + Acteur);
            var NomAct = "";
            var idAct = "";
            if (Acteur !== "") {
                Acteur = Acteur.split("@");
                idAct = Acteur[0];
                NomAct = Acteur[1];
            }
            var texte = document.getElementById("texte").value;
            console.log(Ordre);
            if (texte === " " || Ordre === "") {
                console.log("niditra tato")
                alert("Texte ou ordre vide!!!!");
            } else {
                if (!listeOrdre.includes(Ordre)) {
                    let btr = document.createElement("tr");
                    btr.setAttribute("id", "Deroulement" + d);
                    let id = "Deroulement" + i;
                    let cellAuteur = document.createElement("td");
                    let cellEmotion = document.createElement("td");
                    let cellTexte = document.createElement("td");
                    let cellOrdre = document.createElement("td");
                    let cellDuree = document.createElement("td");
                    let cellTextTexte = document.createTextNode(texte);
                    let cellTextAuteur = document.createTextNode(NomAct);
                    let cellTextDuree = document.createTextNode(Duree);
                    let cellTextEmotion = document.createTextNode(emotion);

                    let cellButton = document.createElement("td");
                    let cellModif = document.createElement("td");

                    let hrefButton = document.createElement("a");
                    let hrefModif = document.createElement("a");

                    let textButton = document.createTextNode("Supprimer");
                    let textModif = document.createTextNode("Modifier");

                    let inputOrdre = createInput("Ordrevalue" + d, Ordre);
                    form.appendChild(inputOrdre);

                    let inputEmotion = createInput("Emotionvalue" + d, emotion);
                    form.appendChild(inputEmotion);

                    let inputActeur = createInput("Acteurvalue" + d, idAct);
                    form.appendChild(inputActeur);

                    let inputDuree = createInput("Dureevalue" + d, Duree);
                    form.appendChild(inputDuree);

                    let inputText = createInput("Textevalue" + d, texte);
                    form.appendChild(inputText);

                    hrefButton.setAttribute("class", "form-group btn btn-primary col-lg-12");
                    hrefModif.setAttribute("class", "form-group btn btn-primary col-lg-12");
                    hrefModif.setAttribute("onclick", "Modifier('" + d + "')");
                    hrefButton.setAttribute("onclick", "supprimerDeroulement('" + d + "')");
                    hrefButton.appendChild(textButton);
                    cellButton.appendChild(hrefButton);
                    cellAuteur.appendChild(cellTextAuteur);
                    cellEmotion.appendChild(cellTextEmotion);
                    cellTexte.appendChild(cellTextTexte);
                    cellDuree.appendChild(cellTextDuree);
                    let cellOrdreInput = document.createElement("input");
                    cellOrdreInput.setAttribute("class", "form-control");
                    cellOrdreInput.setAttribute("id", "Ordre" + d);
                    cellOrdreInput.setAttribute("value", Ordre);
                    cellOrdreInput.setAttribute("type", "text");
                    cellOrdre.appendChild(cellOrdreInput);
                    hrefModif.appendChild(textModif);
                    cellModif.appendChild(hrefModif);
                    btr.appendChild(cellEmotion);
                    btr.appendChild(cellAuteur);
                    btr.appendChild(cellTexte);
                    btr.appendChild(cellDuree);
                    btr.appendChild(cellOrdre);
                    btr.appendChild(cellButton);
                    btr.appendChild(cellModif);
                    tabDeroulement.appendChild(btr);
                    listeOrdre.push(Ordre);
                    document.getElementById("nbrscenario").value = d;
                    document.getElementById("lacteur").value = listeActeur;
                    d++;
                } else {
                    alert("Ordre deja donné");
                }
            }

            //console.log(texte);
        }

        function Modifier(id) {
            var value = document.getElementById("Ordre" + id);
            var form = document.getElementById("form");
            var ordre = document.getElementById("Ordrevalue");
            let index = listeOrdre.indexOf(ordre.value);
            if (listeOrdre.includes(value.value)) {
                alert("Ordre deja donné");
                value.value = ordre.value;
            } else {
                listeOrdre.splice(index, 1);
                ordre.setAttribute("value", value.value);
            }
            document.getElementById("nbrscenario").value = d;
            document.getElementById("lacteur").value = listeActeur;
        }

        function supprimerDeroulement(id) {
            var tabDerouelement = document.getElementById("tbDeroulement");
            var form = document.getElementById("form");
            var sup = document.getElementById("Deroulement" + id);
            var ordre = document.getElementById("Ordre" + id);
            var ordre2 = document.getElementById("Ordrevalue" + id);
            let index = listeOrdre.indexOf(ordre.value);
            listeOrdre.splice(index, 1);
            form.removeChild(ordre2);
            document.getElementById("nbrscenario").value = d;
            document.getElementById("lacteur").value = listeActeur;
            form.removeChild(document.getElementById("Emotionvalue" + id));
            form.removeChild(document.getElementById("Acteurvalue" + id));
            form.removeChild(document.getElementById("Textevalue" + id));
            form.removeChild(document.getElementById("Dureevalue" + id));
            tabDerouelement.removeChild(sup);
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
                <h4 class="text-center widget-title">Ajout de scène</h4>
                <form action="<%= request.getContextPath()%>/AjoutSceneSubmit" method="POST" id="form">
                    <label>Titre</label>
                    <input type="text" class="form-control" placeholder="Titre" name="Titre"/>
                    <label class="mt-3">Plateaux</label>
                    <select class="form-control" name="idPlateaux">
                        <option value="">Plateaux</option>
                        <% for (Plateaux p : lp) {%>
                        <option value="<%= p.getId()%>"><%= p.getNom()%></option>
                        <% } %>
                    </select>
                    <label class="mt-3">Statut:</label>
                    <select name="Statut" class="form-control mb-3 mb-lg-0">
                        <option value="0">ecriture En cours</option>
                        <option value="1">ecriture Termine</option>
                    </select>
                    <table class="table" id="tbActeur">
                        <tr>
                            <th>Acteur</th>
                            <th></th>
                        </tr>
                        <tr>
                            <td>
                                <select class="form-control" id="idActeur">
                                    <option value="">Acteur</option>
                                    <% for (Profil pr : la) {%>
                                    <option value="<%= pr.getId() + " " + pr.getNom()%>"><%= pr.getNom()%></option>
                                    <% }%>
                                </select>
                            </td>
                            <td><a class="form-group btn btn-primary col-lg-3" onclick="AjoutActeur()">Ajouter</a> </td>
                        </tr>
                    </table>
                    <label>Deroulement</label>
                    <table class="table" id="tbDeroulement">
                        <tr>
                            <th>Emotion</th>
                            <th>Acteur</th>
                            <th>Texte</th>
                            <th>Duree</th>
                            <th>Ordre</th>
                            <th></th>
                            <th></th>
                        </tr>
                        <tr>
                            <td>
                                <select class="form-control" id="Emotion">
                                    <option value="">Emotion</option>
                                    <% for (Emotion e : le) {%>
                                    <option value="<%= e.getDesignation()%>"><%= e.getDesignation()%></option>
                                    <% }%>
                                </select>
                            </td>
                            <td>
                                <select class="form-control" id="idActeurDialogue">
                                    <option value="">Acteur</option>
                                    <% for (Profil pr : la) {%>
                                    <option value="<%= pr.getId() + "@" + pr.getNom()%>"><%= pr.getNom()%></option>
                                    <% }%>
                                </select>
                            </td>
                            <td><textarea class="form-control" id="texte" rows="1"> </textarea></td>
                            <td><input type="time" class="form-control" placeholder="Duree" id="Duree"/></td>
                            <td><input type="text" class="form-control" id="Ordre" placeholder="Ordre"/></td>
                            <td><a class="form-group btn btn-primary col-lg-12" onclick="AjouterDeroulement()">Ajouter</a> </td>
                            <td></td>
                        </tr>
                    </table>

                    <input type="hidden" name="idFilm" value="<%= idFilm%>"/>
                    <input type="hidden" name="nbrscenario" id="nbrscenario" value=""/>
                    <input type="hidden" name="lacteur" id="lacteur" value=""/>
                    <input type="submit" class="btn btn-primary mt-3 col-lg-12" onclick="validation()" value="Valider" />
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
