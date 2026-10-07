<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Usuarios Registrados</title>
</head>
<body>
    <h2>Registro exitoso</h2>
    <h3>Lista de usuarios en la base de datos</h3>

    <table border="1" cellpadding="5">
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Email</th>
            <th>Fecha de registro</th>
        </tr>
        <%
            List<String[]> usuarios = (List<String[]>) request.getAttribute("usuarios");
            if (usuarios != null) {
                for (String[] u : usuarios) {
        %>
        <tr>
            <td><%= u[0] %></td>
            <td><%= u[1] %></td>
            <td><%= u[2] %></td>
            <td><%= u[3] %></td>
        </tr>
        <%
                }
            }
        %>
    </table>
    <br>
    <a href="index.html">Registrar otro usuario</a>
</body>
</html>
