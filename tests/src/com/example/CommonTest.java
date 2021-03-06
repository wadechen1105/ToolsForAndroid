package com.example;

import android.content.Context;
import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.appwidget.ExchangeRate;
import com.example.appwidget.L;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.example.appwidget.ExchangeRate.CurrencyType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class CommonTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.tool", appContext.getPackageName());
    }

    @Test
    public void getRawJsonFileInfo() throws IOException {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        Resources r = appContext.getResources();
        InputStream in = r.openRawResource(r.getIdentifier(
                "mfh010",
                "raw",
                appContext.getPackageName()
        ));

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuffer buffer = new StringBuffer();
        String line = "";

        while ((line = reader.readLine()) != null) {
            buffer.append(line + "\n");
        }

        ExchangeRate er = ExchangeRate.fromJsonString(buffer.toString());

        assertEquals(er.getClientTime(), "1410423032490");
        assertTrue(!er.getCurrencyList().isEmpty());

        for (CurrencyType c : er.getCurrencyList()) {
            L.i(c.getCurrencyName1());
        }

    }
}