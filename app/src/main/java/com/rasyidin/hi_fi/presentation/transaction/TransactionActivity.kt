package com.rasyidin.hi_fi.presentation.transaction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rasyidin.hi_fi.R
import com.rasyidin.hi_fi.databinding.ActivityTransactionBinding
import com.rasyidin.hi_fi.presentation.component.ActivityBinding

class TransactionActivity : ActivityBinding<ActivityTransactionBinding>() {

    override fun inflateViewBinding(): ActivityTransactionBinding {
        return ActivityTransactionBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
}