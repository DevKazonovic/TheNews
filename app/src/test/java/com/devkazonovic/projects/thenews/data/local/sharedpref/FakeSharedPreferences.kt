package com.devkazonovic.projects.thenews.data.local.sharedpref

import android.content.SharedPreferences

class FakeSharedPreferences : SharedPreferences {

    private  val KEY_LANGUAGE_ZONE = "Current Selected Language-Zone"
    private val map = mutableMapOf<String?,Any?>()
    private var listener : SharedPreferences.OnSharedPreferenceChangeListener?=null

    override fun getAll(): MutableMap<String?, Any?> {
        return map
    }

    override fun getString(p0: String?, p1: String?): String? {
        return (map[p0] as String?) ?: p1
    }

    override fun getStringSet(p0: String?, p1: MutableSet<String>?): MutableSet<String> {
        return mutableSetOf()
    }

    override fun getInt(p0: String?, p1: Int): Int {
        return 0
    }

    override fun getLong(p0: String?, p1: Long): Long {
        return 0L
    }

    override fun getFloat(p0: String?, p1: Float): Float {
        return 0f
    }

    override fun getBoolean(p0: String?, p1: Boolean): Boolean {
        return true
    }

    override fun contains(p0: String?): Boolean {
        return map.containsKey(p0)
    }

    override fun edit(): SharedPreferences.Editor {
        return FakeSharedPreferenceEditor(this)
    }

    override fun registerOnSharedPreferenceChangeListener(p0: SharedPreferences.OnSharedPreferenceChangeListener?) {
        listener = p0
    }

    override fun unregisterOnSharedPreferenceChangeListener(p0: SharedPreferences.OnSharedPreferenceChangeListener?) {
        listener = null
    }

    inner class FakeSharedPreferenceEditor(private val preferences: SharedPreferences) : SharedPreferences.Editor{
        override fun putString(p0: String?, p1: String?): SharedPreferences.Editor {
            map[p0] = p1
            listener?.onSharedPreferenceChanged(preferences,p0)
            return this
        }

        override fun putStringSet(p0: String?, p1: MutableSet<String>?): SharedPreferences.Editor {
            return this
        }

        override fun putInt(p0: String?, p1: Int): SharedPreferences.Editor {
            return this
        }

        override fun putLong(p0: String?, p1: Long): SharedPreferences.Editor {
            return this
        }

        override fun putFloat(p0: String?, p1: Float): SharedPreferences.Editor {
            return this
        }

        override fun putBoolean(p0: String?, p1: Boolean): SharedPreferences.Editor {
            return this
        }

        override fun remove(p0: String?): SharedPreferences.Editor {
            map.remove(p0)
            return this
        }

        override fun clear(): SharedPreferences.Editor {
            map.clear()
            return this
        }

        override fun commit(): Boolean {
            return true
        }

        override fun apply() {

        }
    }
}