package com.example.guidetovladivostok.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guidetovladivostok.R
import com.example.guidetovladivostok.activity.common.NetworkIsConnectedService
import com.example.guidetovladivostok.adapter.ListLocationAdapter
import com.example.guidetovladivostok.adapter.OnClickListener
import com.example.guidetovladivostok.adapter.OnLongClickListener
import com.example.guidetovladivostok.dto.MarkerDto
import com.example.guidetovladivostok.presenter.ListLocationContract
import com.example.guidetovladivostok.presenter.ListLocationPresenter

/** Активность отвечающая за показ списка локаций **/
class ListLocationActivity: AppCompatActivity(), ListLocationContract.View<List<MarkerDto>> {

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var textListIsEmpty: TextView
    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var adapter: ListLocationAdapter
    private lateinit var presenter: ListLocationContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_location)
        init()
        isConnected()
    }

    override fun onResume() {
        super.onResume()
        showList()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    private fun initToolbar(){
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Список локаций"
    }

    private fun init(){

        initToolbar()

        val onClickListener = object : OnClickListener<MarkerDto>{
            override fun onClick(entity: MarkerDto) {
                startFragment(entity)
            }
        }

        val onLongClickListener = object : OnLongClickListener<MarkerDto>{
            override fun onLongClick(entity: MarkerDto) {
                val dialogFragment = DeleteDialogFragment(entity.getDocumentId(), entity.getNameLocation())
                dialogFragment.show(supportFragmentManager, "dialog")
            }
        }
        presenter = ListLocationPresenter(this)
        textListIsEmpty = findViewById(R.id.textListIsEmpty)
        recyclerView = findViewById(R.id.recyclerViewListLocation)
        constraintLayout = findViewById(R.id.constraintLayoutListLocationActivity)
        adapter = ListLocationAdapter(onClickListener, onLongClickListener)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    private fun startFragment(markerDto: MarkerDto){
        val bundle = Bundle()
        bundle.putSerializable(KeyValue.KEY_MARKER_DTO, markerDto)
        bundle.putBoolean(KeyValue.KEY_IS_CREATE, false)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.constraintLayoutListLocationActivity, CreateOrChangeLocationFragment::class.java, bundle)
            .commit()
    }

    private fun showList(){
        presenter.showListLocation()
    }

    override fun showListLocation(v: List<MarkerDto>) {
        if(v.isNotEmpty()){
            textListIsEmpty.visibility = TextView.GONE
        } else{
            textListIsEmpty.visibility = TextView.VISIBLE
        }
        adapter.deleteList()
        adapter.setList(v.toMutableList())
    }

    private fun isConnected(){
        val networkIsConnectedService: NetworkIsConnectedService =
            ViewModelProvider(this)[NetworkIsConnectedService::class.java]
        networkIsConnectedService.isConnected(
            networkIsConnectedService,
            this,
            constraintLayout
        )
    }
}