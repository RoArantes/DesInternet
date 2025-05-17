package org.example.constant;

public class Constant {
    public static final String API_BASE_URL = "/api/v1"; // Um prefixo base para todas as APIs
    public static final String AUTH_URL = API_BASE_URL + "/auth";
    public static final String API_LOGIN = AUTH_URL + "/login";
    public static final String API_REGISTER = AUTH_URL + "/register"; // Novo endpoint para registro

    public static final String API_USUARIOS = API_BASE_URL + "/usuarios";
    public static final String API_NOTAS_ALUNOS = API_BASE_URL + "/notas-alunos";

    // Removendo a constante antiga se não for mais usada
    // public static final String API_CUSTOMER = API_URL + "/customer"; // Se o 'customer' não existir mais
}