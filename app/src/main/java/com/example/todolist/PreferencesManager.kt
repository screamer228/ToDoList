import android.content.Context
import android.preference.PreferenceManager

object PreferencesManager {
    private const val CHECKBOX_KEY_PREFIX = "checkbox_preference_item_"

    fun saveCheckboxState(context: Context, position: Int, isChecked: Boolean) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putBoolean("$CHECKBOX_KEY_PREFIX$position", isChecked)
        editor.apply()
    }

    fun loadCheckboxState(context: Context, position: Int): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getBoolean("$CHECKBOX_KEY_PREFIX$position", false)
    }
}
