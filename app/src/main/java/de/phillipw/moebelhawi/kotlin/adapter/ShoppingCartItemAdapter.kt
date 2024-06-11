package de.phillipw.moebelhawi.kotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.phillipw.moebelhawi.kotlin.data.models.ShoppingCartItem
import de.phillipw.moebelhawi.kotlin.databinding.ItemShoppingCardBinding

class ShoppingCartItemAdapter (

    private val dataset: List<ShoppingCartItem>,

    private val itemClick: (ShoppingCartItem) -> Unit


): RecyclerView.Adapter<ShoppingCartItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: ItemShoppingCardBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val binding = ItemShoppingCardBinding.inflate(layoutInflater,parent,false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val shoppingCartItem = dataset[position]


        holder.binding.ivItemCart
        holder.binding.tvProductidItem.text = shoppingCartItem.productId
        holder.binding.tvPriceItemCart.text = String.format("%.2f €",shoppingCartItem.price)
        holder.binding.tvQuantityItemCart.text = "${shoppingCartItem.quantity}x"
        holder.binding.btnDeleteFromCart.setOnClickListener {
            itemClick(shoppingCartItem)
        }

    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}