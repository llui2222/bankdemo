package com.bsfdv.bankdemo.service.exception

import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.persistence.EntityNotFoundException

@Slf4j
@RestControllerAdvice
class RestExceptionHandler {
    @ExceptionHandler(EntityNotFoundException::class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    protected fun handleEntityNotFound(ex: EntityNotFoundException): Error {
        return Error(ex.message, ex)
    }

    @ExceptionHandler(NotEnoughFundsOnFromAccount::class)
    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    protected fun handlePhoneIsAlreadyBooked(ex: NotEnoughFundsOnFromAccount): Error {
        return Error("not enough funds on account: ${ex.from} to transfer", ex)
    }
}