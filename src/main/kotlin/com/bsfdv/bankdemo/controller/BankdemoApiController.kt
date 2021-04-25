package com.bsfdv.bankdemo.controller


import com.bsfdv.bankdemo.api.AccountsApi
import com.bsfdv.bankdemo.api.TransferApi
import com.bsfdv.bankdemo.model.Account
import com.bsfdv.bankdemo.model.Transfer
import com.bsfdv.bankdemo.service.BankdemoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.NativeWebRequest
import java.util.*

@RestController
class BankdemoApiController(val bankdemoService: BankdemoService) : AccountsApi, TransferApi {
    override fun getRequest(): Optional<NativeWebRequest> {
        return Optional.empty()
    }

    override fun exposeAccountById(account: Long): ResponseEntity<Account> {
        return ResponseEntity.ok(bankdemoService.exposeAccountById(account))
    }

    override fun transferFromTo(transfer: Transfer): ResponseEntity<Void> {
        bankdemoService.transfer(transfer)
        return ResponseEntity.status(HttpStatus.OK).build()
    }
}