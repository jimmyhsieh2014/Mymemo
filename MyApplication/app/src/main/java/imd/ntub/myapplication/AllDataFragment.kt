// AllDataFragment.kt
package imd.ntub.myapplication

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AllDataFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DataAdapter
    private lateinit var dbHelper: DBHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all_data, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        dbHelper = DBHelper(context as Context)
        adapter = DataAdapter(dbHelper.getAllData(), { data ->
            val fragment = AddEditFragment()
            val bundle = Bundle()
            bundle.putParcelable("data", data)
            fragment.arguments = bundle
            fragmentManager?.beginTransaction()?.replace(R.id.fragment_container, fragment)?.addToBackStack(null)?.commit()
        }, { data ->
            MaterialAlertDialogBuilder(context as Context)
                .setTitle("確認刪除")
                .setMessage("你確定要刪除此資料嗎？")
                .setNegativeButton("取消", null)
                .setPositiveButton("確定") { _, _ ->
                    dbHelper.deleteData(data.id)
                    adapter.updateData(dbHelper.getAllData())
                    Toast.makeText(context, "資料已刪除", Toast.LENGTH_SHORT).show()
                }
                .show()
        }, { phone ->
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$phone")
            startActivity(intent)
        })
        recyclerView.adapter = adapter
        return view
    }
}