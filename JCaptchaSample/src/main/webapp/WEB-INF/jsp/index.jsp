<%@ page session="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
    <head>
        <title>Captcha</title>
    </head>
    <body>
        <form method="post" action="validate">
            <img src="captcha.jpg"/>
            <input type="text" name="j_captcha_response" />
            <input type="submit" value="Submit" name="_finish"/>
        </form>
    </body>
</html>