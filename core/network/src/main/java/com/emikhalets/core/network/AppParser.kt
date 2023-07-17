package com.emikhalets.core.network

import com.emikhalets.core.common.logi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

abstract class AppParser {

    protected suspend fun parseSource(url: String): Document {
        logi(TAG, "Parse source: url = $url")
        return withContext(Dispatchers.IO) {
            Jsoup.connect(url).get()
        }
    }

    protected suspend fun Document.getElements(query: String): Elements {
        logi(TAG, "Get elements: query = $query")
        return withContext(Dispatchers.IO) {
            this@getElements.select(query)
        }
    }

    companion object {

        private const val TAG = "AppParser"
    }
}