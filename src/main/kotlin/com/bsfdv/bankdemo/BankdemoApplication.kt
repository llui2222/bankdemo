package com.bsfdv.bankdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BankdemoApplication

fun main(args: Array<String>) {
    runApplication<BankdemoApplication>(*args)
}
