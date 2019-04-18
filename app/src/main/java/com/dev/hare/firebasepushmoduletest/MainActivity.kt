package com.dev.hare.firebasepushmoduletest

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.CookieManager
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView.setWebContentsDebuggingEnabled
import com.dev.hare.firebasepushmodule.util.Logger
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FCMUtil.getToken()
        init()
    }

    @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
    private fun init(){
        webview.apply {
            scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY

            //웹뷰 속도개선
            if (Build.VERSION.SDK_INT >= 19) {
                setLayerType(View.LAYER_TYPE_HARDWARE, null)
            } else {
                setLayerType(View.LAYER_TYPE_SOFTWARE, null)
            }

            clearHistory()
            clearFormData()
            clearCache(true)
            setWebContentsDebuggingEnabled(true)
            clearView()

            addJavascriptInterface(this, "push")
            webChromeClient = WebChromeClient()
        }
        webview.settings.apply {
            if (Build.VERSION.SDK_INT >= 21) {
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                val cookieManager = CookieManager.getInstance()
                cookieManager.setAcceptCookie(true)
                cookieManager.setAcceptThirdPartyCookies(webview, true)
            }

            useWideViewPort = true
            this.setSupportZoom(true)
            builtInZoomControls = true
            //줌컨트롤러 안보이게 처리
            displayZoomControls = false
            cacheMode = WebSettings.LOAD_NO_CACHE
            setSavePassword(false)
            setAppCacheEnabled(true)
            domStorageEnabled = true
            javaScriptEnabled = true
            allowFileAccess = true
            allowFileAccessFromFileURLs = true //Maybe you don't need this rule
            allowUniversalAccessFromFileURLs = true
            //mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically( true );

            javaScriptCanOpenWindowsAutomatically = true
            this.setSupportMultipleWindows(true)
            layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
            setRenderPriority(WebSettings.RenderPriority.HIGH)
        }

        webview.loadUrl("http://web-dev.enter6.co.kr/")

    }

    /*
     * @javascript
     *
     * push.login($user_code);
     * */
    @JavascriptInterface
    fun login(user_code: String) {
        Logger.log(Logger.LogType.INFO, "usecode : $user_code")
        FCMHttpService.updateTokenWithUserCode(user_code);
    }
}
