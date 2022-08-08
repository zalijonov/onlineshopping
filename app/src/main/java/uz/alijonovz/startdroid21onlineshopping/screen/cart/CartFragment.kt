package uz.alijonovz.startdroid21onlineshopping.screen.cart

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import uz.alijonovz.startdroid21onlineshopping.adapter.CartAdapter
import uz.alijonovz.startdroid21onlineshopping.databinding.FragmentCartBinding
import uz.alijonovz.startdroid21onlineshopping.model.ProductModel
import uz.alijonovz.startdroid21onlineshopping.screen.MainViewModel
import uz.alijonovz.startdroid21onlineshopping.screen.makeorder.MakeOrderActivity
import uz.alijonovz.startdroid21onlineshopping.utils.Constants
import uz.alijonovz.startdroid21onlineshopping.utils.PrefUtils
import java.io.Serializable

class CartFragment : Fragment() {

    lateinit var binding: FragmentCartBinding
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        viewModel.progress.observe(requireActivity(), Observer{
            binding.swipe.isRefreshing = it
        })

        viewModel.productData.observe(requireActivity(), Observer{
            binding.recyclerView.adapter = CartAdapter(it)
        })
        viewModel.error.observe(requireActivity(), Observer{
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        })

        binding.btnMakeOrder.setOnClickListener{
            startActivity(Intent(requireActivity(), MakeOrderActivity::class.java))
        }
        loadData()
        binding.swipe.setOnRefreshListener {
            loadData()
        }

        binding.btnMakeOrder.setOnClickListener {
            val intent = Intent(requireActivity(), MakeOrderActivity::class.java)
            intent.putExtra(Constants.EXTRA_DATA, (viewModel.productData.value ?: emptyList<ProductModel>()) as Serializable)
            startActivity(intent)
        }
    }

    fun loadData(){
        viewModel.loadProductByIds(PrefUtils.getCartList().map{it.product_id})
    }
    companion object {

        @JvmStatic
        fun newInstance() = CartFragment()
    }
}