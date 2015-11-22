
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="REFRESH" content="5;url=<%=request.getContextPath()%>">
        <title>Informations Erreur</title>
       
        <link rel="stylesheet" type="text/css" href="/resources/themes/cofares.css" >

    </head>
    <body>
        <div id="index-content">           
            <% 
                try {
                   session.invalidate();
            } catch(IllegalStateException e) {} 
            %>
              <p>Utilisateur ou mot de passe erroné / Ou permission non accordée</p>
               
               <p>Revenir à la page 
                   <strong><a href="<%=request.getContextPath()%>"> d’authentification </a></strong>.
               </p>
        </div>
    </body>
</html>
