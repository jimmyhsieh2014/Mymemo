// AddEditFragment.kt
package imd.ntub.myapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class AddEditFragment : Fragment() {

    private lateinit var nameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var dbHelper: DBHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_edit, container, false)
        nameEditText = view.findViewById(R.id.name_edit_text)
        phoneEditText = view.findViewById(R.id.phone_edit_text)
        saveButton = view.findViewById(R.id.save_button)
        dbHelper = DBHelper(context as Context)

        val data: Data? = arguments?.getParcelable("data")
        data?.let {
            nameEditText.setText(it.name)
            phoneEditText.setText(it.phone)
        }

        saveButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val phone = phoneEditText.text.toString()
            if (name.isNotEmpty() && phone.isNotEmpty()) {
                if (data == null) {
                    dbHelper.insertData(name, phone)
                    Toast.makeText(context, "資料已新增", Toast.LENGTH_SHORT).show()
                } else {
                    dbHelper.updateData(data.id, name, phone)
                    Toast.makeText(context, "資料已更新", Toast.LENGTH_SHORT).show()
                }
                fragmentManager?.popBackStack()
            } else {
                Toast.makeText(context, "請填寫完整資料", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }
}