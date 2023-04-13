package com.example.guidetovladivostok.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.guidetovladivostok.R
import com.example.guidetovladivostok.dto.MarkerDto

/** Адаптер для представления информации в RecyclerView **/
class ListLocationAdapter constructor(
    onClickListener: OnClickListener<MarkerDto>,
    onLongClickListener: OnLongClickListener<MarkerDto>
) : RecyclerView.Adapter<ListLocationAdapter.LocationViewHolder>() {

    private var onClickListener: OnClickListener<MarkerDto>
    private var onLongClickListener: OnLongClickListener<MarkerDto>
    private var locationList: MutableList<MarkerDto> = ArrayList()

    init {
        this.onClickListener = onClickListener
        this.onLongClickListener = onLongClickListener
    }

    fun setList(locationList: MutableList<MarkerDto>) {
        this.locationList = locationList
        notifyDataSetChanged()
    }

    fun deleteList() {
        locationList.clear()
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_location, parent, false)
        return LocationViewHolder(view)
    }

    override fun getItemCount(): Int {
        return locationList.size
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val locationEntity = locationList[position]
        holder.textViewNameLocation.text = locationEntity.getNameLocation()
        holder.cardView.setOnClickListener {
            onClickListener.onClick(locationEntity)
        }
        holder.cardView.setOnLongClickListener {
            onLongClickListener.onLongClick(locationEntity)
            true
        }
    }

    class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val cardView: CardView
        val textViewNameLocation: TextView

        init {
            cardView = itemView.findViewById(R.id.cardItemLocation)
            textViewNameLocation = itemView.findViewById(R.id.nameLocation)
        }

    }
}