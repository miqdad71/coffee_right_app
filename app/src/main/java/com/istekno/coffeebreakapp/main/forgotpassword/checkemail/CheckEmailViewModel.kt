package com.istekno.coffeebreakapp.main.forgotpassword.checkemail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class CheckEmailViewModel : ViewModel(), CoroutineScope {
    private lateinit var service: CheckEmailApi

    val onSuccessLiveData = MutableLiveData<Boolean>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()
    val onSetAccountId = MutableLiveData<Int>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(service: CheckEmailApi) {
        this@CheckEmailViewModel.service = service
    }

    fun serviceApi(email: String) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    service.checkEmail(
                        email = email
                    )
                } catch (e: HttpException) {
                    withContext(Dispatchers.Main) {
                        onSuccessLiveData.value = false

                        when {
                            e.code() == 404 -> {
                                onFailLiveData.value = "Account not registered"
                            }
                            else -> {
                                onFailLiveData.value =
                                    "Fail to check email! Please try again later!"
                            }
                        }
                    }
                }
            }

            if (response is VerifyEmailResponse) {
                isLoadingLiveData.value = false

                if (response.success) {
                    onSuccessLiveData.value = true
                    onSetAccountId.value = response.data[0].acId
                } else {
                    onFailLiveData.value = response.message
                }
            }
        }
    }
}
