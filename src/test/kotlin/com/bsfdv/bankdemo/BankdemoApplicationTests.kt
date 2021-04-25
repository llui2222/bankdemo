package com.bsfdv.bankdemo

import com.bsfdv.bankdemo.entity.AccountDB
import com.bsfdv.bankdemo.repository.BankdemoRepository
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
class BankdemoApplicationTests{

	@Autowired
	lateinit var bankdemoRepository: BankdemoRepository

	@Autowired
	lateinit var mockMvc: MockMvc

    @Test
    fun contextLoads() {
    }

    @Test
    fun testAnonymous() {
        mockMvc.perform(get("/fakeEndpoint"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun exposeAccountById_positive() {
        bankdemoRepository.save(AccountDB(1,55555555,10))
        mockMvc.perform(
            get("/v1/accounts/55555555")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id", `is`(1)))
            .andExpect(jsonPath("$.account", `is`(55555555)))
            .andExpect(jsonPath("$.value", `is`(10)))
    }
    @Test
    fun exposeAccountById_negative() {
        mockMvc.perform(
            get("/v1/accounts/77777777")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().isNotFound)
    }

    @Test
    fun transfer() {
        bankdemoRepository.save(AccountDB(1,55555555,10))
        bankdemoRepository.save(AccountDB(2,33333333,15))
        mockMvc.perform(
            post("/v1/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"from\":\"33333333\", \"to\":\"55555555\", \"value\":\"5\"}")
        )
            .andExpect(status().isOk)

        mockMvc.perform(
            get("/v1/accounts/55555555")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id", `is`(1)))
            .andExpect(jsonPath("$.account", `is`(55555555)))
            .andExpect(jsonPath("$.value", `is`(15)))

        mockMvc.perform(
            get("/v1/accounts/33333333")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id", `is`(2)))
            .andExpect(jsonPath("$.account", `is`(33333333)))
            .andExpect(jsonPath("$.value", `is`(10)))
    }
    @Test
    fun transfer_NegativeNotenoughFund() {
        bankdemoRepository.save(AccountDB(1,55555555,10))
        bankdemoRepository.save(AccountDB(2,33333333,15))
        mockMvc.perform(
            post("/v1/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"from\":\"33333333\", \"to\":\"55555555\", \"value\":\"25\"}")
        )
            .andExpect(status().isNotAcceptable)
    }
}
