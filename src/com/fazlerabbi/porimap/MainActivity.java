package com.fazlerabbi.porimap;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        webView = new WebView(this);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        
        webView.setWebViewClient(new WebViewClient());
        
        // Load your local HTML interface (we'll place index.html in the assets folder)
        webView.loadUrl("file:///android_asset/index.html");
        
        setContentView(webView);
    }

    // Bridge physical phone keypad / D-pad clicks to JavaScript events
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        String keyName = "";
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP: keyName = "UP"; break;
            case KeyEvent.KEYCODE_DPAD_DOWN: keyName = "DOWN"; break;
            case KeyEvent.KEYCODE_DPAD_LEFT: keyName = "LEFT"; break;
            case KeyEvent.KEYCODE_DPAD_RIGHT: keyName = "RIGHT"; break;
            case KeyEvent.KEYCODE_ENTER:
            case KeyEvent.KEYCODE_DPAD_CENTER: keyName = "A"; break;
            case KeyEvent.KEYCODE_BACK: keyName = "B"; break;
        }
        
        if (!keyName.isEmpty()) {
            webView.evaluateJavascript("if(window.handlePhysicalKey) { window.handlePhysicalKey('" + keyName + "'); }", null);
            return true; // Consume the event
        }
        
        return super.onKeyDown(keyCode, event);
    }
}
