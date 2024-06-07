package de.phillipw.moebelhawi.kotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import de.phillipw.moebelhawi.kotlin.data.models.Product
import de.phillipw.moebelhawi.kotlin.databinding.ItemProductBinding
import de.phillipw.moebelhawi.kotlin.databinding.ItemSearchResultBinding


class SearchAdapter (

    private val dataset: List<Product>,

    private val searchClick: (Product) -> Unit

): RecyclerView.Adapter<SearchAdapter.ItemViewHolder>(){

    inner class ItemViewHolder(val binding: ItemSearchResultBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
       val context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val binding = ItemSearchResultBinding.inflate(layoutInflater,parent,false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val product = dataset[position]

        val thumbnailUrl = product.thumbnails.firstOrNull()?.lastOrNull()  // Hier wird eine Liste an Strings angezeigt ?! Er übergibt im Detail alle Images

        if (thumbnailUrl != null){
            holder.binding.ivSearchImage.load(thumbnailUrl)
        }
        holder.binding.tvSearchResult.text = product.title
        holder.binding.tvSearchPrice.text = String.format("%.2f €",product.price)

        holder.binding.ivSearchImage.setOnClickListener {
            searchClick(product)
        }
    }
    override fun getItemCount(): Int {
        return dataset.size
    }
}