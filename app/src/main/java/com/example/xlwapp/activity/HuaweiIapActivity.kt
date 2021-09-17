package com.example.xlwapp.activity

import android.app.Activity
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.os.Bundle
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.xlwapp.R
import com.huawei.hmf.tasks.OnFailureListener
import com.huawei.hmf.tasks.OnSuccessListener
import com.huawei.hmf.tasks.Task
import com.huawei.hms.iap.Iap
import com.huawei.hms.iap.IapApiException
import com.huawei.hms.iap.IapClient
import com.huawei.hms.iap.entity.*
import com.huawei.hms.iap.util.IapClientHelper
import com.huawei.hms.support.api.client.Status
import org.json.JSONException
import java.io.UnsupportedEncodingException
import java.security.*
import java.security.spec.InvalidKeySpecException
import java.security.spec.X509EncodedKeySpec


class HuaweiIapActivity : AppCompatActivity() {

    private val PUBLIC_KEY = "MIIBojANBgkqhkiG9w0BAQEFAAOCAY8AMIIBigKCAYEAojZ7UOzN1Yss7QaLm7QIzuJ+0Qp4nOmrhqFvZ1ClZ1zdF6yhYuxoeuKvH+Z+zfGwrGGiusjwCqIHATeLoRS50V13fLaTh49qaqcsjBMjEd2on08XJj5tdIJ4pAPyYkesABfgEjnL9gLGR+TtgS0Qt/6whLH63Y1tbT3yrJYriODRenlZx1xkFq9EDLWtBL+cip6aDg3NBQMF4XTo0osMb0ob6ZSZgRdfZK7WDHq/o5YhEMu4N84xgUt4JMh/2Gn7dJMymwowUBy+9RoUiBsuCRcxyawq0W1VyoRvJg18ms2lUHnJ7C/8tbm8giJgyFuPwgl5yelE3YlMFMH7Dc+dmh5w4JbBLWtJOszWl4gdo+VcOxtnbZdS2MwSE+fM6dQQ9KFaq7n4OrwG82Q/MsK0BAejC1oblvlr+QociXKQJwzkqDbHwavy0MI9Q5eQrsSoZZ4ONuqtsxH3AFtqVlvY8UMmNl++lGH0TDgxpC5FSz951bl0mCGGFCZPcSI40Op1AgMBAAE="


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_huawei_iap)
        queryIsReady()
    }

    private fun jumpToPurchasePage() {
        // 创建一个StartIapActivityReq对象
        val req = PurchaseIntentReq()
        req.priceType = IapClient.PriceType.IN_APP_SUBSCRIPTION
        req.productId = "pipi1haodingyue"
        req.developerPayload = "test"
        // 获取调用接口的Activity对象
        val mClient = Iap.getIapClient(this)
            val task = mClient.createPurchaseIntent(req)
        task.addOnSuccessListener { result ->
            Log.i("startIapActivity", "onSuccess")
            // 请求成功，需拉起IAP返回的页面
            result.status.startResolutionForResult(this, 8888)

        }.addOnFailureListener { Log.e("startIapActivity", "onFailure") }
    }

    private fun queryIsReady() {
        // 获取调用接口的Activity对象
        val task: Task<IsEnvReadyResult> = Iap.getIapClient(this).isEnvReady
        task.addOnSuccessListener(object : OnSuccessListener<IsEnvReadyResult?> {
            override fun onSuccess(result: IsEnvReadyResult?) {
                // 获取接口请求的结果
                getProdectInfo()
                querySubscriptions()
            }
        }).addOnFailureListener(object : OnFailureListener {
            override fun onFailure(e: Exception?) {
                if (e is IapApiException) {
                    val status: Status = e.status
                    if (status.getStatusCode() === OrderStatusCode.ORDER_HWID_NOT_LOGIN) {
                        // 未登录帐号
                        Toast.makeText(this@HuaweiIapActivity, "未登录帐号", Toast.LENGTH_LONG).show()
                        if (status.hasResolution()) {
                            try {
                                // 6666是开发者自定义的常量
                                // 启动IAP返回的登录页面
                                status.startResolutionForResult(this@HuaweiIapActivity, 6666)
                            } catch (exp: SendIntentException) {
                                exp.printStackTrace()
                            }
                        }
                    } else if (status.getStatusCode() === OrderStatusCode.ORDER_ACCOUNT_AREA_NOT_SUPPORTED) {
                        // 用户当前登录的华为帐号所在的服务地不在华为IAP支持结算的国家或地区中
                        Toast.makeText(this@HuaweiIapActivity, "用户当前登录的华为帐号所在的服务地不在华为IAP支持结算的国家或地区中", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 6666) {
            if (data != null) {
                // 获取接口请求结果
                val returnCode = IapClientHelper.parseRespCodeFromIntent(data)
                Log.e("test", returnCode.toString())
            }
        }
        if (requestCode == 8888) {
            if (resultCode == Activity.RESULT_OK) {
                val purchaseResult: Int = getPurchaseResult(this, data)
                if (OrderStatusCode.ORDER_PRODUCT_OWNED == purchaseResult) {
                    Toast.makeText(this, "you have had this product", Toast.LENGTH_SHORT).show()
                    return
                }
                if (OrderStatusCode.ORDER_STATE_SUCCESS == purchaseResult) {
                    Toast.makeText(this, "pay success", Toast.LENGTH_SHORT).show()
                    return
                }
                if (OrderStatusCode.ORDER_STATE_CANCEL == purchaseResult) {
                    Toast.makeText(this, "user cancel", Toast.LENGTH_SHORT).show()
                    return
                }
                Toast.makeText(this,"pay fail", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "cancel subscribe", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getProdectInfo(){
        val productIdList: MutableList<String> = ArrayList()
        // 查询的商品必须是开发者在AppGallery Connect网站配置的商品
        productIdList.add("pipi1haodingyue")
        val req = ProductInfoReq()
        req.priceType = IapClient.PriceType.IN_APP_SUBSCRIPTION
        req.productIds = productIdList
        // 获取调用接口的Activity对象
        // 调用obtainProductInfo接口获取AppGallery Connect网站配置的商品的详情信息
        val task = Iap.getIapClient(this).obtainProductInfo(req)
        task.addOnSuccessListener { result -> // 获取接口请求成功时返回的商品详情信息
            val productList = result.productInfoList
            showProduct(productList[0])
        }.addOnFailureListener { e ->
           e.printStackTrace()
        }
    }

    private fun querySubscriptions() {
        val req = OwnedPurchasesReq()
        req.priceType = IapClient.PriceType.IN_APP_SUBSCRIPTION
        val task: Task<OwnedPurchasesResult> = Iap.getIapClient(this).obtainOwnedPurchases(req)
        task.addOnSuccessListener { result ->
            Log.i("test", "obtainOwnedPurchases, success")
            Toast.makeText(this, "obtainOwnedPurchases, success", Toast.LENGTH_LONG).show()
        }.addOnFailureListener { e ->
            Log.e("test", "obtainOwnedPurchases, fail")
            Toast.makeText(this, "obtainOwnedPurchases, fail", Toast.LENGTH_LONG).show()
        }
    }

    private fun showProduct(productInfo: ProductInfo) {
        val productName = findViewById<TextView>(R.id.product_name)
        val productDesc = findViewById<TextView>(R.id.product_desc)
        val price = findViewById<TextView>(R.id.price)
        productName.text = productInfo.productName
        productDesc.text = productInfo.productDesc
        price.text = productInfo.price
        findViewById<Button>(R.id.action).setOnClickListener {
            jumpToPurchasePage()
        }
    }

    fun getPurchaseResult(activity: Activity?, data: Intent?): Int {
        val purchaseResultInfo = Iap.getIapClient(activity).parsePurchaseResultInfoFromIntent(data)
        if (null == purchaseResultInfo) {
            return OrderStatusCode.ORDER_STATE_FAILED
        }
        val returnCode = purchaseResultInfo.returnCode
        val errMsg = purchaseResultInfo.errMsg
        return when (returnCode) {
            OrderStatusCode.ORDER_PRODUCT_OWNED -> {
                OrderStatusCode.ORDER_PRODUCT_OWNED
            }
            OrderStatusCode.ORDER_STATE_SUCCESS -> {
                val credible: Boolean = doCheck(purchaseResultInfo.inAppPurchaseData, purchaseResultInfo.inAppDataSignature, PUBLIC_KEY)
                if (credible) {
                    try {
                        val inAppPurchaseData = InAppPurchaseData(purchaseResultInfo.inAppPurchaseData)
                        if (inAppPurchaseData.isSubValid) {
                            return OrderStatusCode.ORDER_STATE_SUCCESS
                        }
                    } catch (e: JSONException) {
                        return OrderStatusCode.ORDER_STATE_FAILED
                    }
                } else {
                }
                OrderStatusCode.ORDER_STATE_FAILED
            }
            else -> {
                returnCode
            }
        }
    }

    fun doCheck(content: String, sign: String?, publicKey: String?): Boolean {
        if (TextUtils.isEmpty(publicKey)) {
            Log.e("test", "publicKey is null")
            return false
        }
        if (TextUtils.isEmpty(content) || TextUtils.isEmpty(sign)) {
            Log.e("test", "data is error")
            return false
        }
        try {
            val keyFactory = KeyFactory.getInstance("RSA")
            val encodedKey = Base64.decode(publicKey, Base64.DEFAULT)
            val pubKey = keyFactory.generatePublic(X509EncodedKeySpec(encodedKey))
            val signature = Signature.getInstance("SHA256WithRSA")
            signature.initVerify(pubKey)
            signature.update(content.toByteArray(charset("utf-8")))
            return signature.verify(Base64.decode(sign, Base64.DEFAULT))
        } catch (e: NoSuchAlgorithmException) {
            Log.e("test", "doCheck NoSuchAlgorithmException$e")
        } catch (e: InvalidKeySpecException) {
            Log.e("test", "doCheck InvalidKeySpecException$e")
        } catch (e: InvalidKeyException) {
            Log.e("test", "doCheck InvalidKeyException$e")
        } catch (e: SignatureException) {
            Log.e("test", "doCheck SignatureException$e")
        } catch (e: UnsupportedEncodingException) {
            Log.e("test", "doCheck UnsupportedEncodingException$e")
        }
        return false
    }

}