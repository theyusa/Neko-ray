package com.neko.v2ray.util

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.google.gson.Gson
import com.neko.v2ray.dto.RulesetItem
import com.neko.v2ray.util.MmkvManager.subStorage
import java.util.Collections

object SettingsManager {

    fun initRoutingRulesets(context: Context, index: Int = 0) {
        val exist = MmkvManager.decodeRoutingRulesets()

        val fileName = when (index) {
            0 -> "custom_routing_white"
            1 -> "custom_routing_black"
            2 -> "custom_routing_global"
            else -> "custom_routing_white"
        }
        if (exist.isNullOrEmpty()) {
            val assets = Utils.readTextFromAssets(context, fileName)
            if (TextUtils.isEmpty(assets)) {
                return
            }

            val rulesetList = Gson().fromJson(assets, Array<RulesetItem>::class.java).toMutableList()
            MmkvManager.encodeRoutingRulesets(rulesetList)
        }
    }

    fun resetRoutingRulesets(context: Context, index: Int) {
        MmkvManager.encodeRoutingRulesets(null)
        initRoutingRulesets(context, index)
    }

    fun getRoutingRuleset(index: Int): RulesetItem? {
        if (index < 0) return null

        val rulesetList = MmkvManager.decodeRoutingRulesets()
        if (rulesetList.isNullOrEmpty()) return null

        return rulesetList[index]
    }

    fun saveRoutingRuleset(index: Int, ruleset: RulesetItem?) {
        if (ruleset == null) return

        val rulesetList = MmkvManager.decodeRoutingRulesets()
        if (rulesetList.isNullOrEmpty()) return

        if (index < 0 || index >= rulesetList.count()) {
            rulesetList.add(ruleset)
        } else {
            rulesetList[index] = ruleset
        }
        MmkvManager.encodeRoutingRulesets(rulesetList)
    }

    fun removeRoutingRuleset(index: Int) {
        if (index < 0) return

        val rulesetList = MmkvManager.decodeRoutingRulesets()
        if (rulesetList.isNullOrEmpty()) return

        rulesetList.removeAt(index)
        MmkvManager.encodeRoutingRulesets(rulesetList)
    }

    fun routingRulesetsBypassLan(): Boolean {
        val rulesetItems = MmkvManager.decodeRoutingRulesets()
        val exist = rulesetItems?.any { it.enabled && it.domain?.contains(":private") == true }
        return exist == true
    }

    fun swapRoutingRuleset(fromPosition: Int, toPosition: Int) {
        val rulesetList = MmkvManager.decodeRoutingRulesets()
        if (rulesetList.isNullOrEmpty()) return

        Collections.swap(rulesetList, fromPosition, toPosition)
        MmkvManager.encodeRoutingRulesets(rulesetList)
    }

    fun swapSubscriptions(fromPosition: Int, toPosition: Int) {
        val subsList = MmkvManager.decodeSubsList()
        if (subsList.isNullOrEmpty()) return

        Collections.swap(subsList, fromPosition, toPosition)
        MmkvManager.encodeSubsList(subsList)
    }


}
