package com.rasyidin.hi_fi.domain.usecase.transaction

import com.rasyidin.hi_fi.data.repository.TransactionRepository
import com.rasyidin.hi_fi.domain.model.transaction.Transaction

class DeleteTransaction(private val repository: TransactionRepository) {

    suspend operator fun invoke(transaction: Transaction) {
        repository.deleteTransaction(transaction.toEntity())
    }
}