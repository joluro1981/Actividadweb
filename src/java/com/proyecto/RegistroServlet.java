package com.proyecto;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegistroServlet")
public class RegistroServlet extends HttpServlet {

    // ---- Datos de conexion: ajusta usuario/clave si tu MySQL los tiene distintos ----
    private static final String URL = "jdbc:mysql://localhost:3306/actividadweb";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");

        List<String[]> listaUsuarios = new ArrayList<>();

        try {
            // 1. Cargar el driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. Crear la conexion con DriverManager
            try (Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD)) {

                // 3. Insertar el nuevo usuario
                String sqlInsert = "INSERT INTO usuarios (nombre, email) VALUES (?, ?)";
                try (PreparedStatement pst = conexion.prepareStatement(sqlInsert)) {
                    pst.setString(1, nombre);
                    pst.setString(2, email);
                    pst.executeUpdate();
                }

                // 4. Consultar todos los usuarios para mostrarlos en el JSP
                String sqlConsulta = "SELECT id, nombre, email, fecha_registro FROM usuarios ORDER BY id";
                try (Statement st = conexion.createStatement();
                     ResultSet rs = st.executeQuery(sqlConsulta)) {

                    while (rs.next()) {
                        listaUsuarios.add(new String[]{
                            String.valueOf(rs.getInt("id")),
                            rs.getString("nombre"),
                            rs.getString("email"),
                            String.valueOf(rs.getTimestamp("fecha_registro"))
                        });
                    }
                }
            }

        } catch (ClassNotFoundException | SQLException e) {
        }

        request.setAttribute("usuarios", listaUsuarios);
        RequestDispatcher rd = request.getRequestDispatcher("resultado.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("index.html");
    }
}
