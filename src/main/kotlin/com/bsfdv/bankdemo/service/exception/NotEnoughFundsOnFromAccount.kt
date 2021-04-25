package com.bsfdv.bankdemo.service.exception

class NotEnoughFundsOnFromAccount(val from: Long) : RuntimeException()
