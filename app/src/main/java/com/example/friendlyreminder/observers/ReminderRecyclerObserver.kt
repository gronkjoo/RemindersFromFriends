package com.example.friendlyreminder.observers

import android.util.Log
import androidx.recyclerview.widget.RecyclerView

class ReminderRecyclerObserver: RecyclerView.AdapterDataObserver() {
    val TAG = "RecylerViewAdapterObserver"
    override fun onChanged() {
        super.onChanged()
        Log.d(TAG,"Called the onchanged for recyclerview")
    }
}