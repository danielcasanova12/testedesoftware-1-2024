package br.edu.utfpr.bankapi.controller;

import br.edu.utfpr.bankapi.model.Account;
import jakarta.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@Transactional
class TransactionControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    TestEntityManager entityManager;

    Account account;

    @BeforeEach
    void setup() {
        account = new Account("Lauro Lima", 12346, 1000, 0);
        entityManager.persist(account);
    }

    @Test
    void shouldReturn400ForInvalidRequestDeposit() throws Exception {
        var json = "{}";

        var res = mvc.perform(
                MockMvcRequestBuilders.post("/transaction/deposit")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(400, res.getStatus());
    }

    @Test
    void shouldReturn201ForValidRequestDeposit() throws Exception {
        var json = """
                {
                    "receiverAccountNumber": 12346,
                    "amount": 200
                }
                    """;

        var res = mvc.perform(
                MockMvcRequestBuilders.post("/transaction/deposit")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(201, res.getStatus());
        Assertions.assertEquals("application/json", res.getContentType());
    }

    @Test
    void shouldReturnCorrectDataInJsonDeposit() throws Exception {
        var json = """
                {
                    "receiverAccountNumber": 12346,
                    "amount": 200
                }
                    """;

        mvc.perform(
                MockMvcRequestBuilders.post("/transaction/deposit")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.receiverAccount.number", Matchers.equalTo(12346)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount", Matchers.equalTo(200.0)));
    }

    @Test
    void shouldReturn400ForInvalidRequestWithdraw() throws Exception {
        var json = "{}";

        var res = mvc.perform(
                MockMvcRequestBuilders.post("/transaction/withdraw")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(400, res.getStatus());
    }

    @Test
    void shouldReturn201ForValidRequestWithdraw() throws Exception {
        var json = """
                {
                    "sourceAccountNumber": 12346,
                    "amount": 200
                }
                    """;

        var res = mvc.perform(
                MockMvcRequestBuilders.post("/transaction/withdraw")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(201, res.getStatus());
        Assertions.assertEquals(MediaType.APPLICATION_JSON_VALUE, res.getContentType());
        var responseBody = res.getContentAsString();
        Assertions.assertTrue(responseBody.contains("\"amount\":200"));
        Assertions.assertTrue(responseBody.contains("\"balance\":800"));
        Assertions.assertTrue(responseBody.contains("\"number\":12346"));
    }

    @Test
    void shouldReturn400WhenAmountIsGreaterThanBalanceWithdraw() throws Exception {
        var json = """
                {
                    "sourceAccountNumber": 12346,
                    "amount": 2000
                }
                    """;

        var res = mvc.perform(
                MockMvcRequestBuilders.post("/transaction/withdraw")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(400, res.getStatus());
    }

    @Test
    void shouldReturn400WhenAccountDoesNotExistWithdraw() throws Exception {
        var json = """
                {
                    "sourceAccountNumber": 11111,
                    "amount": 200
                }
                    """;

        var res = mvc.perform(
                MockMvcRequestBuilders.post("/transaction/withdraw")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(400, res.getStatus());
    }

    @Test
    void shouldReturn201WhenRequestIsOkTransfer() throws Exception {
        entityManager.persist(new Account("Pix da Silva", 12345, 500, 0));
        var json = """
                {
                    "sourceAccountNumber": 12345,
                    "receiverAccountNumber": 12346,
                    "amount": 200
                }""";

        mvc.perform(
                MockMvcRequestBuilders.post("/transaction/transfer")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount", Matchers.equalTo(200.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sourceAccount.number", Matchers.equalTo(12345)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.receiverAccount.number", Matchers.equalTo(12346)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sourceAccount.balance", Matchers.equalTo(300.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.receiverAccount.balance", Matchers.equalTo(1200.0)));
    }

    @Test
    void shouldReturn400WhenEmptyBodyTransfer() throws Exception {
        var json = "{}";

        var res = mvc.perform(
                MockMvcRequestBuilders.post("/transaction/transfer")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(400, res.getStatus());
    }

    @Test
    void shouldReturn400WhenSourceAccountDoesNotExistTransfer() throws Exception {
        var json = """
                {
                    "sourceAccountNumber": 11111,
                    "receiverAccountNumber": 12346,
                    "amount": 200
                }""";

        var res = mvc.perform(
                MockMvcRequestBuilders.post("/transaction/transfer")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(400, res.getStatus());
    }

    @Test
    void shouldReturn400WhenReceiverAccountDoesNotExistTransfer() throws Exception {
        var json = """
                {
                    "sourceAccountNumber": 12346,
                    "receiverAccountNumber": 11111,
                    "amount": 200
                }""";

        var res = mvc.perform(
                MockMvcRequestBuilders.post("/transaction/transfer")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(400, res.getStatus());
    }

    @Test
    void shouldReturn400WhenTransferAmountIsGreaterThanBalanceTransfer() throws Exception {
        var json = """
                {
                    "sourceAccountNumber": 12346,
                    "receiverAccountNumber": 12345,
                    "amount": 2000
                }""";

        var res = mvc.perform(
                MockMvcRequestBuilders.post("/transaction/transfer")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(400, res.getStatus());
    }
}