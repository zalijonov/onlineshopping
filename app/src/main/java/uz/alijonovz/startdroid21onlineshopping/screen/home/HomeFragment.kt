package uz.alijonovz.startdroid21onlineshopping.screen.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import uz.alijonovz.startdroid21onlineshopping.adapter.*
import uz.alijonovz.startdroid21onlineshopping.databinding.FragmentHomeBinding
import uz.alijonovz.startdroid21onlineshopping.model.CategoryModel
import uz.alijonovz.startdroid21onlineshopping.screen.MainViewModel

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipe.setOnRefreshListener{
            loadData()
        }
        viewModel.error.observe(requireActivity(), Observer{
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        })

        viewModel.progress.observe(requireActivity(), Observer{
            binding.swipe.isRefreshing = it
        })

        viewModel.offersData.observe(requireActivity(), Observer{
            binding.viewPager.adapter = ViewPagerAdapter(requireActivity(), it)
            binding.viewPager.autoScroll(2000)
        })

        viewModel.categoriesData.observe(requireActivity(), Observer{
            binding.category.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            binding.category.adapter = CategoryAdapter(it, object: CategoryAdapterCallback{
                override fun onClickItem(item: CategoryModel) {
                    viewModel.loadCategoryProducts(item.id)
                }
            })
        })

        viewModel.topProductData.observe(requireActivity(), Observer{
            binding.tvProduct.layoutManager = LinearLayoutManager(requireActivity())
            binding.tvProduct.adapter = ProductAdapter(it)
        })

        loadData()

    }

    fun loadData(){
//        viewModel.loadCategories()
        viewModel.loadOffers()
//        viewModel.loadTopProducts()
        viewModel.loadAllDBProducts()
        viewModel.loadAllDBCategories()

    }

companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}