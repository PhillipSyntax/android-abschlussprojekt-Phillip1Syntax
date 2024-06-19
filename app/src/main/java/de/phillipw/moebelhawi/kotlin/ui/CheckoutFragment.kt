package de.phillipw.moebelhawi.kotlin.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.phillipw.moebelhawi.kotlin.HomeViewModel
import de.phillipw.moebelhawi.kotlin.R
import de.phillipw.moebelhawi.kotlin.adapter.CheckoutAdapter
import de.phillipw.moebelhawi.kotlin.databinding.FragmentCheckoutBinding


class CheckoutFragment : Fragment() {

    private lateinit var binding: FragmentCheckoutBinding

    private val viewModel: HomeViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCheckoutBinding.inflate(layoutInflater)
        viewModel.getCartItems()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.cartItems.observe(viewLifecycleOwner) { shoppingCartItem ->
            viewModel.calculateSubTotalPrice()
            viewModel.calculateDeliveryPrice(shoppingCartItem)
            binding.rvImageCheckout.adapter = CheckoutAdapter(shoppingCartItem)
            viewModel.calculateTotalPrice()
        }
        viewModel.subTotalPrice.observe(viewLifecycleOwner) { subTotalPrice ->
            binding.tvSumCheckout.text = "subprice: %.2f €".format(subTotalPrice)
        }
        viewModel.deliveryCost.observe(viewLifecycleOwner) { deliveryPrice ->
            binding.tvDelivery.text = "delivery: %.2f €".format(deliveryPrice)
        }
        viewModel.totalPriceWithDelivery.observe(viewLifecycleOwner) { deliveryPrice ->
            binding.tvEndpriceCheckout.text = "Total Price: %.2f €".format(deliveryPrice)
        }

        binding.btnConfirm.setOnClickListener {

            val correctName = "^[a-zA-Z ]+$".toRegex()

            val correctPlz = "^[0-9]{5}$".toRegex()

            val correctEmail = "^[A-Za-z0-9+_.-]+@(.+)$".toRegex()

            val firstName = binding.firstNameEt.text.toString()
            val secondName = binding.secondNameEt.text.toString()
            val address = binding.addressEt.text.toString()
            val place = binding.placeEt.text.toString()
            val zip = binding.zipEt.text.toString()
            val email = binding.emailEt.text.toString()

            if (firstName.isNotBlank()
                && firstName.matches(correctName)
                && secondName.isNotBlank()
                && secondName.matches(correctName)
                && address.isNotBlank()
                && place.isNotBlank()
                && zip.isNotBlank()
                && zip.matches(correctPlz)
                && email.isNotBlank()
                && email.matches(correctEmail)
            ) {
                viewModel.deleteAllCartItems()
                showAlert()
                findNavController().navigate(R.id.homeFragment)
            } else {
                if (firstName.isBlank() || !firstName.matches(correctName)) {
                    Toast.makeText(requireContext(), "please fill a correct first name", Toast.LENGTH_SHORT)
                        .show()
                } else if (secondName.isBlank() || !secondName.matches(correctName)) {
                    Toast.makeText(
                        requireContext(),
                        "please fill a correct second name",
                        Toast.LENGTH_SHORT)
                        .show()

                } else if (zip.isBlank() || !zip.matches(correctPlz)) {
                    Toast.makeText(
                        requireContext(),
                        "please fill a correct zip",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (email.isBlank() || !email.matches(correctEmail)) {
                    Toast.makeText(
                        requireContext(),"please check your email adress",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    Toast.makeText(requireContext(), "please fill all fields!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        binding.ivBackCheckout.setOnClickListener {
            backAlert()
        }
    }
    private fun showAlert() {
        val builder = AlertDialog.Builder(requireContext())
        //builder.setView(R.layout.) Wird noch implementiert
        builder.setTitle("Vielen Dank für Ihre Bestellung!")
        builder.setMessage("Sie erhalten per E-Mail eine Bestätigung.")

        builder.setPositiveButton("OK") { dialog, which ->
            dialog.dismiss()

            // Positive Button Click Logic
        }
//        builder.setNegativeButton("Abbrechen") { dialog, which ->
//            // Negative Button Click Logic
//            dialog.dismiss()
//        }
        builder.show()
    }

    private fun backAlert() {
        val builder = AlertDialog.Builder(requireContext())
        //builder.setView(R.layout.) Wird noch implementiert
        builder.setTitle("Möchten Sie wirklich abbrechen ?")


        builder.setPositiveButton("JA") { dialog, which ->
            dialog.dismiss()
            findNavController().navigateUp()

            // Positive Button Click Logic
        }
        builder.setNegativeButton("Abbrechen") { dialog, which ->
            // Negative Button Click Logic
            dialog.dismiss()
        }
        builder.show()
    }
}