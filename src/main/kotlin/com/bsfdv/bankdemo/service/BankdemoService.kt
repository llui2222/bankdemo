package com.bsfdv.bankdemo.service

import com.bsfdv.bankdemo.model.Account
import com.bsfdv.bankdemo.model.Transfer

interface BankdemoService {

    fun exposeAccountById(account: Long): Account
    fun transfer(transfer: Transfer)
}
