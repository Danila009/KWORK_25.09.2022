package com.example.films.data.network.freekassaOrder.model

const val FREEKASSA_ORDER_STATUS_NEW = 0
const val FREEKASSA_ORDER_STATUS_PAID = 1
const val FREEKASSA_ORDER_STATUS_ERROR = 8
const val FREEKASSA_ORDER_STATUS_CANCEL = 9

data class FreekassaOrderBody(
    val shopId:Int,
    val nonce:String,
    val signature:String,
    val paymentId:String? = null
)

data class FreekassaOrderResponse(
    val type:String,
    val pages:Int,
    val orders:List<FreekassaOrderResponseItem>
)

data class FreekassaOrderResponseItem(
    val merchant_order_id:String,
    val fk_order_id:Int,
    val amount:Float,
    val currency:String,
    val email:String,
    val account:String,
    val date:String,
    val status:Int
)