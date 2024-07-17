package uz.yasindev.noteapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.yasindev.noteapp.R
import uz.yasindev.noteapp.database.NoteModel
import uz.yasindev.noteapp.databinding.RecyclerItemBinding

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.MyViewHolder>() {

    private val data = ArrayList<NoteModel>()
    var onClickItemListener: ((noteModel: NoteModel) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<NoteModel>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.MyViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        RecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val binding =
            RecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteAdapter.MyViewHolder, position: Int) {
        holder.binding.edittextView.text = data[position].description
        holder.binding.date.text = data[position].date
        holder.binding.title.text = data[position].title

        holder.binding.root.setOnClickListener {
            onClickItemListener?.invoke(
                NoteModel(
                    data[position].id,
                    data[position].title,
                    data[position].description,
                    data[position].priority,
                    data[position].date
                )
            )

        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class MyViewHolder(val binding: RecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

}