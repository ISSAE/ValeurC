<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Entry Form</title>
    </head>
    <body>

        <h1>Entry Form</h1>
          <jsp:useBean id="mybean" scope="session" class="net.cofares.NameHandler" />
           <jsp:setProperty name="mybean" property="*"  />
           <form method="post">
            <table>
                <tr><td>Enter your name:</td><td> 
                        <input type="text" name="name" value="${mybean.name}" /></td></tr>
               
                <tr><td colspan="2"><input type="submit" value="OK" /></td></tr>
            </table>
        </form>

    </body>
</html>
