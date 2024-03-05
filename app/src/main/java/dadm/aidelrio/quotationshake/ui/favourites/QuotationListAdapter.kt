package dadm.aidelrio.quotationshake.ui.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dadm.aidelrio.quotationshake.databinding.QuotationItemBinding
import dadm.aidelrio.quotationshake.domain.model.Quotation

class QuotationListAdapter(private val onItemClick: (String) -> Unit) : ListAdapter<Quotation, QuotationListAdapter.ViewHolder>(
    QuotationDiff
) {

    object QuotationDiff : DiffUtil.ItemCallback<Quotation>() {
        override fun areItemsTheSame(oldItem: Quotation, newItem: Quotation): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Quotation, newItem: Quotation): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(QuotationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false), onItemClick)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: QuotationItemBinding, val onItemClick: (String) -> Unit) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClick(binding.authorTextView.text.toString())
            }
        }

        fun bind(quotation: Quotation) {
            binding.authorTextView.text = quotation.author
            binding.citeTextView.text = quotation.text
        }
    }


}