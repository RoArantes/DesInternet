package br.com.exemplo.sistemavet.controller;

class AuthResponse {
    public String jwt;

    public AuthResponse(String jwt) {
        this.jwt = jwt;
    }
}
