package com.bsfdv.bankdemo.entity

import javax.persistence.*


@Entity
data class AccountDB(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(nullable = false)
    var account: Long,

    @Column(name = "debit", nullable = false)
    var value: Long
)