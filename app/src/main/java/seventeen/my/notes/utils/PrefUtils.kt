package seventeen.my.notes.utils

import android.content.Context
import android.content.SharedPreferences

class PrefUtils(val context: Context) {
    companion object {


        private const val PREFS_NAME = "notes"


        fun save(context: Context, KEY_NAME: String, text: String) {
            val sharedPref: SharedPreferences =
                context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPref.edit()
            editor.putString(KEY_NAME, text)
            editor.apply()
        }
        fun getValueString(context: Context,KEY_NAME: String): String? {

            val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

            return sharedPref.getString(KEY_NAME, null)


        }

        fun save(context: Context,KEY_NAME: String, value: Int) {
            val sharedPref: SharedPreferences =
                context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPref.edit()

            editor.putInt(KEY_NAME, value)

            editor.apply()
        }

        fun save(context: Context,KEY_NAME: String, status: Boolean) {
            val sharedPref: SharedPreferences =
                context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPref.edit()

            editor.putBoolean(KEY_NAME, status)

            editor.apply()
        }



        fun getValueInt(context: Context,KEY_NAME: String): Int {
            val sharedPref: SharedPreferences =
                context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            return sharedPref.getInt(KEY_NAME, 0)
        }

        fun getValueBoolean(context: Context, KEY_NAME: String, defaultValue: Boolean): Boolean {
            val sharedPref: SharedPreferences =
                context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            return sharedPref.getBoolean(KEY_NAME, defaultValue)

        }
        fun getValueBoolean(context: Context, KEY_NAME: String): Boolean {
            val sharedPref: SharedPreferences =
                context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            return sharedPref.getBoolean(KEY_NAME, false)

        }

        fun clearSharedPreference(context: Context) {
            val sharedPref: SharedPreferences =
                context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPref.edit()

            //sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

            editor.clear()
            editor.apply()
        }

        fun removeValue(context: Context,KEY_NAME: String) {
            val sharedPref: SharedPreferences =
                context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPref.edit()

            editor.remove(KEY_NAME)
            editor.apply()
        }

        fun setArrayPrefs(context: Context,
            arrayName: String,
            array: ArrayList<String?>
        ) {
            val sharedPref: SharedPreferences =
                context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putInt(arrayName + "_size", array.size)
            for (i in 0 until array.size) editor.putString(arrayName + "_" + i, array[i])
            editor.apply()
        }

        fun getArrayPrefs(
            context: Context,
            arrayName: String
        ): ArrayList<String?>? {
            val sharedPref: SharedPreferences =
                context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val size = sharedPref.getInt(arrayName + "_size", 0)
            val array: ArrayList<String?> = ArrayList(size)
            for (i in 0 until size) array.add(sharedPref.getString(arrayName + "_" + i, null))
            return array
        }

    }
}