package com.istekno.coffeebreakapp.main.maincontent.order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.istekno.coffeebreakapp.utilities.SharedPreferenceUtil
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class OrderViewModel: ViewModel(), CoroutineScope {

    val listData = MutableLiveData<List<OrderResponse.Data>>()
    val getListData = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()
    private val isNotFound = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    private lateinit var service: OrderService
    private lateinit var sharedPref : SharedPreferenceUtil

    fun setService(service: OrderService) {
        this.service = service
    }

    fun setSharedPref(sharedPref: SharedPreferenceUtil) {
        this.sharedPref = sharedPref
    }

    fun callOrderApi() {
        launch {
            isLoading.value = true

            val result = withContext(Dispatchers.IO) {
                try {
                    service.getOrderByCsId(sharedPref.getPreference().roleID!!)
                } catch (e: Throwable) {
                    e.printStackTrace()

                    withContext(Dispatchers.Main) {
                        getListData.value = false
                        isLoading.value = false
                        isNotFound.value = true
                    }
                }
            }

            if (result is OrderResponse) {
                if (result.success) {
                    getListData.value = result.success
                    val list = result.data.map {
                        OrderResponse.Data(it.orderDetailId, it.customerId, it.deliveryId, it.priceBeforeTax, it.couponId, it.totalPrice, it.orderDetailStatus, it.orderPayment, it.orderTax, it.orderCreated, it.orderUpdated)
                    }
                    val mutable = list.toMutableList()
                    mutable.removeAll { it.orderDetailStatus == "Done" }
                    listData.value = mutable
                    isLoading.value = false
                } else {
                    getListData.value = false
                    isLoading.value = false
                }
            }
        }
    }

}