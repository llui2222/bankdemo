package com.bsfdv.bankdemo.service

import com.bsfdv.bankdemo.model.Account
import com.bsfdv.bankdemo.model.Transfer
import com.bsfdv.bankdemo.repository.BankdemoRepository
import com.bsfdv.bankdemo.service.exception.NotEnoughFundsOnFromAccount
import com.bsfdv.bankdemo.util.AccountConverter
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityNotFoundException

@Slf4j
@Service
class BankdemoServiceImpl(
    val bankdemoRepository: BankdemoRepository,
    val accountConverter: AccountConverter
) : BankdemoService {

    override fun exposeAccountById(account: Long): Account {
        return accountConverter.convertToDto(
            bankdemoRepository.findByAccount(account).orElseThrow { EntityNotFoundException("no such account: $account") }
        )
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    override fun transfer(transfer: Transfer) {
        val accountFrom = bankdemoRepository.findByAccount(transfer.from)
            .orElseThrow { EntityNotFoundException("no such account: ${transfer.from}") }
        val accountTo = bankdemoRepository.findByAccount(transfer.to)
            .orElseThrow { EntityNotFoundException("no such account: ${transfer.to}") }
        if (accountFrom.value < transfer.value)
            throw NotEnoughFundsOnFromAccount(transfer.from)
        accountFrom.value=accountFrom.value.minus(transfer.value)
        accountTo.value=accountTo.value.plus(transfer.value)
        bankdemoRepository.save(accountFrom)
        bankdemoRepository.save(accountTo)
    }
}
