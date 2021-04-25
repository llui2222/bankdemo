package com.bsfdv.bankdemo.util

import com.bsfdv.bankdemo.entity.AccountDB
import com.bsfdv.bankdemo.model.Account
import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface AccountConverter {
    fun convertToDto(accountDb: AccountDB): Account

    @InheritInverseConfiguration
    fun convertToModel(account: Account): AccountDB
}