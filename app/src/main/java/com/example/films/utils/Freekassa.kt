package com.example.films.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.films.data.network.freekassaOrder.FreekassaOrderRepository
import com.example.films.data.network.freekassaOrder.model.FreekassaOrderBody
import com.example.films.data.network.freekassaOrder.model.FreekassaOrderResponse
import com.example.films.data.network.utils.Result
import com.example.films.utils.Hmac.digest
import java.math.BigInteger
import java.security.MessageDigest
import java.time.Instant
import java.util.*
import javax.inject.Inject

class Freekassa {

    fun payBrowser(
        context: Context,
        shopId: Int,
        price: Int,
        currency: String = "RUB",
        orderNumber: String = UUID.randomUUID().toString(),
        secretWordOne: String,
    ){
        val browserIntent = Intent(Intent.ACTION_VIEW, getPayUri(
            shopId = shopId,
            price = price,
            currency = currency,
            orderNumber = orderNumber,
            secretWordOne = secretWordOne
        ))
        context.startActivity(browserIntent)
    }

    fun getPayUri(
        shopId: Int,
        price: Int,
        currency: String = "RUB",
        orderNumber: String = UUID.randomUUID().toString(),
        secretWordOne: String,
    ):Uri = Uri.parse(
        "https://pay.freekassa.ru/?m=$shopId&oa=$price" +
                "&currency=$currency&o=$orderNumber&s=${signatureGeneration(
                    shopId = shopId,
                    currency = currency,
                    price = price,
                    secretWordOne = secretWordOne,
                    orderNumber = orderNumber
                )}&lang=ru"
    )

    fun signatureGeneration(
        shopId: Int,
        currency: String,
        price: Int,
        secretWordOne: String,
        orderNumber: String,
    ):String {
        return createMd5(
            "$shopId:$price:$secretWordOne:$currency:$orderNumber"
        )
    }

    fun createMd5(input:String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(
            1, md.digest(input.toByteArray())
        ).toString(16)
            .padStart(32, '0')
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createFreekassaOrderBody(
        shopId:Int,
        cashKey: String,
        orderNumber: String
    ):FreekassaOrderBody {
        val unixTime = Instant.now().epochSecond

        return FreekassaOrderBody(
            shopId = shopId,
            nonce = unixTime.toString(),
            signature = digest(
                msg = "$unixTime|$orderNumber|$shopId",
                key = cashKey
            ),
            paymentId = orderNumber
        )
    }
}