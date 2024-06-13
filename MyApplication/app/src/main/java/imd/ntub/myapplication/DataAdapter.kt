// DataAdapter.kt
package imd.ntub.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DataAdapter(
    private var dataList: List<Data>,
    private val onItemClick: (Data) -> Unit,
    private val onDeleteClick: (Data) -> Unit,
    private val onCallClick: (String) -> Unit
) : RecyclerView.Adapter<DataAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.name_text_view)
        val phoneTextView: TextView = itemView.findViewById(R.id.phone_text_view)
        val callButton: Button = itemView.findViewById(R.id.call_button)
        val deleteButton: Button = itemView.findViewById(R.id.delete_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_data, parent, false)
        return DataViewHolder(view)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val data = dataList[position]
        holder.nameTextView.text = data.name
        holder.phoneTextView.text = data.phone
        holder.itemView.setOnClickListener { onItemClick(data) }
        holder.callButton.setOnClickListener { onCallClick(data.phone) }
        holder.deleteButton.setOnClickListener { onDeleteClick(data) }
    }

    override fun getItemCount(): Int = dataList.size

    fun updateData(newDataList: List<Data>) {
        dataList = newDataList
        notifyDataSetChanged()
    }
}