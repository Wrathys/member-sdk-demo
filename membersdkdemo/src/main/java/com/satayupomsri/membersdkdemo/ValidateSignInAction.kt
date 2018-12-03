package com.satayupomsri.membersdkdemo

import android.content.Context
import com.satayupomsri.membersdkdemo.protocol.ServerProtocol
import com.satayupomsri.membersdkdemo.status.MemberStatus
import com.satayupomsri.membersdkdemo.utils.getKey
import java.net.URI

/**
 * Created by satayupomsri on 3/12/2018 AD.
 */
internal class ValidateSignInAction(var context: Context) {

    private companion object {
        val SCHEME_KEY = "scheme"
        val HOST_KEY = "host"
        val PATH_KEY = "path"
        val QUERY_KEY = "query"
        val REG_SCHEME = "^(http|https)"
    }

    private val listValidate = HashMap<String, (isValid: String) -> Boolean>().apply {
        val isScheme: (String?) -> Boolean = { scheme -> scheme != null && scheme.matches(REG_SCHEME.toRegex()) }
        val isHost: (String?) -> Boolean = { host -> host != null && host == keyValidate(ServerProtocol.SIGN_IN_VALIDATE_HOST) }
        val isPath: (String?) -> Boolean = { path -> path != null && path == keyValidate(ServerProtocol.SIGN_IN_VALIDATE_PATH) }
        val isQuery: (String?) -> Boolean = { query -> query != null && !query.isEmpty() }

        put(SCHEME_KEY, isScheme)
        put(HOST_KEY, isHost)
        put(PATH_KEY, isPath)
        put(QUERY_KEY, isQuery)
    }

    private fun keyValidate(keyId: Int): String = getKey(this.context, ServerProtocol.validateSignInName, keyId)

    fun onSignIn(url: String?, handlerSuccess: ((jwt: String?) -> Unit)?, handlerFail: ((status: MemberStatus) -> Unit)?) {
        url?.let {
            val uri = URI.create(url)
            uri?.let {
                var numOfValid = 0
                for (i in listValidate) {
                    when {
                        i.key == SCHEME_KEY && !uri.scheme.isNullOrEmpty() && i.value(uri.scheme) -> numOfValid++
                        i.key == HOST_KEY && !uri.host.isNullOrEmpty() && i.value(uri.host) -> numOfValid++
                        i.key == PATH_KEY && !uri.path.isNullOrEmpty() && i.value(uri.path) -> numOfValid++
                        i.key == QUERY_KEY && !uri.query.isNullOrEmpty() && i.value(uri.query) -> numOfValid++
                        else -> return
                    }
                }

                if (numOfValid == listValidate.size) {
                    val queries = uri.query.split(keyValidate(ServerProtocol.SIGN_IN_SPLIT_QUERY))
                    if (queries.isNotEmpty()) {
                        queries.filter {
                            it.startsWith(keyValidate(ServerProtocol.SIGN_IN_VALIDATE_QUERY))
                        }.forEach {
                            val jwtData = it.split(keyValidate(ServerProtocol.SIGN_IN_SPLIT_JWT_OF_QUERY))
                            if (jwtData.size == 2 && jwtData[1].isNotEmpty()) {
                                handlerSuccess?.invoke(jwtData[1])
                            } else {
                                handlerFail?.invoke(MemberStatus.SIGN_IN_RESPONSE_EMPTY)
                            }
                        }
                    }
                }
            }
        }
    }
}