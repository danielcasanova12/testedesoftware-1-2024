package br.edu.utfpr.bankapi.controller;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.utfpr.bankapi.dto.AccountDTO;

@RestController
@RequestMapping("/account")
public class AccountController {
    @PostMapping
    public ResponseEntity<Object> create(
            @RequestBody AccountDTO dto) {
        return ResponseEntity.ok().build();
    }

}
