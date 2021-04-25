package com.bsfdv.bankdemo.repository

import com.bsfdv.bankdemo.entity.AccountDB
import org.springframework.data.repository.CrudRepository
import java.util.*

interface BankdemoRepository : CrudRepository<AccountDB, Long>{
    fun findByAccount(account: Long): Optional<AccountDB>
}