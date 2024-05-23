package de.phillipw.moebelhawi.kotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import de.phillipw.moebelhawi.kotlin.R
import de.phillipw.moebelhawi.kotlin.data.models.Product
import de.phillipw.moebelhawi.kotlin.databinding.ItemProductBinding

class ProductAdapter (
    private val dataset: List<Product>

): RecyclerView.Adapter<ProductAdapter.ItemViewHolder>(){
    inner class ItemViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
       val context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val binding = ItemProductBinding.inflate(layoutInflater,parent,false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val product = dataset[position]
        holder.binding.ivProductImage.load(product.thumbnails.lastOrNull()) {

        }

        holder.binding.tvProductName.text = product.title
        holder.binding.tvPrice.text = product.price.toString() // Diese Zeile muss geändert werden

    }
}