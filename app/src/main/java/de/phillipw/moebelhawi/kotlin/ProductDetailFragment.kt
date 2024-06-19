package de.phillipw.moebelhawi.kotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import de.phillipw.moebelhawi.kotlin.data.models.Product
import de.phillipw.moebelhawi.kotlin.databinding.FragmentProductsDetailBinding

class ProductDetailFragment : Fragment() {


    private lateinit var binding: FragmentProductsDetailBinding

    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProductsDetailBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.selectedProduct.observe(viewLifecycleOwner) { product ->
           if(product != null) {
               binding.tvTitleDetail.text = product.title
               binding.tvPriceDetail.text = String.format("%.2f €",product.price)
               loadProductImage(product)

               binding.btnAddToCart.setOnClickListener{
                   viewModel.addToShoppingCart(product)
               }
           }
        }
        binding.ivBackButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun loadProductImage(product: Product) {

        val thumbnailUrl = product.thumbnails.firstOrNull()?.lastOrNull()
        if (thumbnailUrl != null) {
            binding.ivDetail.load(thumbnailUrl)
        }
    }
}