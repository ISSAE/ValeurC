<%-- 
    Document   : login
    Created on : 9 déc. 2012, 12:07:18
    Author     : pascalfares
    Mofdified : 19 Nov 2015, 16:54
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login par form</title>
        <link rel="stylesheet" type="text/css" href="/resources/themes/cofares.css" />
    </head>
    <body onload="document.forms[0].j_username.focus()">
        <div id="index-content">
            <H2 ALIGN="center">Bienvenue auditeurs SMB214 ... 2016, exemple login form</H2>
            <H3 ALIGN="center">Accès: mes auditeurs</H3>
           <form action="j_security_check" method="post">
                <table align="center">
                    <tr>
                        <td>Nom utilisateur:</td>
                        <td><input type="text" name="j_username"></td>
                    </tr>
                    <tr>
                        <td>Mot de passe:</td>
                        <td><input type="password" name="j_password"></td>
                    </tr>
                </table>
                <br><p align="center"> <input type="submit" value="Valider"></p>
            </form>
        </div>
    </body>
</html>
