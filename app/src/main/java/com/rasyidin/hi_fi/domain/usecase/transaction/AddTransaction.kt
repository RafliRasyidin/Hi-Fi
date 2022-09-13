package com.rasyidin.hi_fi.domain.usecase.transaction

import com.rasyidin.hi_fi.data.repository.TransactionRepository
import com.rasyidin.hi_fi.domain.model.transaction.Transaction

class AddTransaction(private val repository: TransactionRepository) {

    suspend operator fun invoke(transaction: Transaction) {
        repository.addTransaction(transaction.toEntity())
    }
}