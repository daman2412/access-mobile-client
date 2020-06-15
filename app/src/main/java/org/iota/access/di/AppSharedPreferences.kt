/*
 *  This file is part of the IOTA Access distribution
 *  (https://github.com/iotaledger/access)
 *
 *  Copyright (c) 2020 IOTA Stiftung.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.iota.access.di

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import org.iota.access.SettingsFragment
import org.iota.access.api.model.Command
import org.iota.access.models.User
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Custom [SharedPreferences] class providing different methods for saving data
 */
@Singleton
class AppSharedPreferences @Inject internal constructor(private val sharedPreferences: SharedPreferences, private val gson: Gson) {

    private fun putString(key: String?, value: String?) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String?): String? {
        return sharedPreferences.getString(key, "")
    }

    fun getInt(key: String?): Int {
        return try {
            sharedPreferences.getInt(key, 0)
        } catch (ignored: Exception) {
            try {
                Integer.valueOf(sharedPreferences.getString(key, "0")!!)
            } catch (e: Exception) {
                0
            }
        }
    }

    fun putUser(user: User?) {
        putString(SettingsFragment.Keys.PREF_KEY_USER, gson.toJson(user))
    }

    val user: User?
        get() {
            val userJson = sharedPreferences.getString(SettingsFragment.Keys.PREF_KEY_USER, null)
            return if (userJson?.isEmpty() != false) null else try {
                gson.fromJson(userJson, User::class.java)
            } catch (ignored: JsonSyntaxException) {
                null
            }
        }

    fun putCommandList(commandList: List<Command>?) {
        putString(SettingsFragment.Keys.PREF_KEY_CUSTOM_COMMANDS, gson.toJson(commandList))
    }

    val commandList: List<Command>?
        get() {
            val commandListString = getString(SettingsFragment.Keys.PREF_KEY_CUSTOM_COMMANDS)
            if (commandListString.equals("", ignoreCase = true)) {
                return null
            }
            val type = object : TypeToken<List<Command?>?>() {}.type
            return try {
                gson.fromJson<List<Command>>(commandListString, type)
            } catch (ignored: JsonSyntaxException) {
                null
            }
        }

    fun removeCommandFromList(command: Command) {
        // TODO: 30.1.2019. Fix removing command
        val oldList = commandList?.toMutableList() ?: return
        var i = 0
        val n = oldList.size
        while (i < n) {
            if (oldList[i] == command) {
                oldList.removeAt(i)
                break
            }
            ++i
        }
        putCommandList(oldList)
    }

}
