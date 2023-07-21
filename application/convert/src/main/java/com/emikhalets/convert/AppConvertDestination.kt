package com.emikhalets.convert

import com.emikhalets.core.navigation.AppDestination
import com.emikhalets.feature.currencies_convert.navigation.FeatureCurrenciesConvertDestination

object AppConvertDestination : AppDestination {

    const val NavGraph: String = "graph_application_convert"
    const val BottomBarTrigger: String = FeatureCurrenciesConvertDestination.Currencies
}